package com.weiziplus.springboot.controller.api.wx;

import com.weiziplus.springboot.interceptor.AuthTokenIgnore;
import com.weiziplus.springboot.models.wx.WxDict;
import com.weiziplus.springboot.models.wx.WxTestQuestion;
import com.weiziplus.springboot.service.wx.WxDictDataService;
import com.weiziplus.springboot.service.wx.WxDictRefService;
import com.weiziplus.springboot.service.wx.WxDictService;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/13 0:37
 */

@RestController
@RequestMapping("/api/wx/testing")
@Api(tags = "测验接口")
@Slf4j
public class WxTestingController {

    @Autowired
    WxDictDataService wxDictDataService;

    @Autowired
    WxDictService wxDictService;

    @Autowired
    WxDictRefService wxDictRefService ;


    @ApiOperation(value = "开始测验")
    @PostMapping("/start")
    @AuthTokenIgnore
    public ResultUtils start(@RequestBody WxTestQuestion question)
    {





        return null;
    }
}
