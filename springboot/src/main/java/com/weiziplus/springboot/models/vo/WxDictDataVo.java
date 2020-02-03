package com.weiziplus.springboot.models.vo;

import com.weiziplus.springboot.models.wx.WxDictData;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/2 22:58
 */
public class WxDictDataVo extends WxDictData {

    private Long dicId;

    public Long getDicId() {
        return dicId;
    }

    public void setDicId(Long dicId) {
        this.dicId = dicId;
    }
}
