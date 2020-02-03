package com.weiziplus.springboot.models.dto;

import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.models.wx.WxDictRef;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 22:49
 */
public class WxDictRefDto extends WxDictRef {
    private WxDictData wxDictData;

    public WxDictData getWxDictData() {
        return wxDictData;
    }

    public void setWxDictData(WxDictData wxDictData) {
        this.wxDictData = wxDictData;
    }
}
