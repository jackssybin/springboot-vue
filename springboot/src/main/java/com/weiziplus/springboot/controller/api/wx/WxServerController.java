package com.weiziplus.springboot.controller.api.wx;

import com.weiziplus.springboot.interceptor.AuthTokenIgnore;
import com.weiziplus.springboot.models.wx.WxDict;
import com.weiziplus.springboot.models.wx.WxUser;
import com.weiziplus.springboot.service.wx.WxUserService;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/9 11:48
 */
@RestController
@RequestMapping("/api/wx")
@Api(tags = "词典接口")
@Slf4j
public class WxServerController {

    @Autowired
    private WxUserService wxUserService;

    //code就是小程序从前端传过来的
    @ApiOperation(value = "根据code获取openid")
    @GetMapping("/getWxOpenId")
    @AuthTokenIgnore
    private String getWxOpenId(String code) throws Exception {
        String AppID = "wxa0078cbd64f34279";
        String AppSecret="86389594ae283da765cdc5e6ae7a5840";//这两个都可以从微信公众平台中查找
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + AppID + "&secret=" + AppSecret + "&js_code="
                + code + "&grant_type=authorization_code";
        URL reqURL = new URL(url);
        HttpsURLConnection openConnection = (HttpsURLConnection) reqURL
                .openConnection();
        openConnection.setConnectTimeout(10000);
        //这里我感觉获取openid的时间比较长，不过也可能是我网络的问题，
        //所以设置的响应时间比较长
        openConnection.connect();
        InputStream in = openConnection.getInputStream();

        StringBuilder builder = new StringBuilder();
        BufferedReader bufreader = new BufferedReader(new InputStreamReader(in));
        for (String temp = bufreader.readLine(); temp != null; temp = bufreader
                .readLine()) {
            builder.append(temp);
        }

        String result = builder.toString();
        in.close();
        openConnection.disconnect();
        log.info("根据code:{} 获取的openId：{}",code,result);
        return result;
        //result就是包含openid的键值对，返回给小程序前端即可
    }

    @ApiOperation(value = "新增小程序用户")
    @PostMapping("/addWxUser")
    @AuthTokenIgnore
    public ResultUtils addWxUser(@RequestBody WxUser user)
    {

        log.info("addWxUser:{}",user);
        user.setCreateTime(DateUtils.getNowDateTime());

        return wxUserService.addWxUser(user);
    }

}
