package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.mapper.wx.WxDictMapper;
import com.weiziplus.springboot.models.wx.WxDict;
import com.weiziplus.springboot.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:31
 */
@Service
public class WxDictService {

    @Autowired
    WxDictMapper wxDictMapper;

    public ResultUtils addDict(WxDict data)
    {
        wxDictMapper.insert(data);
        return ResultUtils.success(data.getId());
    }


    public ResultUtils<List<WxDict>> getList(WxDict data)
    {
        return ResultUtils.success(wxDictMapper.getList(data));
    }

}
