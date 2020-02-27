package com.weiziplus.springboot.service.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiziplus.springboot.mapper.wx.WxDictMapper;
import com.weiziplus.springboot.models.wx.WxDict;
import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:31
 */
@Service
@Slf4j
public class WxDictService {

    @Autowired
    WxDictMapper wxDictMapper;

    public ResultUtils addDict(WxDict data)
    {
        List<WxDict> list = wxDictMapper.getList(data);
        if(null==list||list.size()==0){
            wxDictMapper.insert(data);
            return ResultUtils.success(data.getId());
        }else{
            log.info("已经存在相同的词典");
            return ResultUtils.error("已经存在相同的词典");
        }

    }


    public ResultUtils<List<WxDict>> getList(WxDict data)

    {
        return ResultUtils.success(wxDictMapper.getList(data));
    }


}
