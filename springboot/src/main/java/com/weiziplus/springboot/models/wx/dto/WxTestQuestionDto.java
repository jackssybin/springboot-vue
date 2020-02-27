package com.weiziplus.springboot.models.wx.dto;

import com.weiziplus.springboot.models.wx.WxTestQuestion;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/27 17:22
 */

public class WxTestQuestionDto extends WxTestQuestion {

    @ApiModelProperty("是否重新生成")
    private boolean needForceTest;

    public boolean isNeedForceTest() {
        return needForceTest;
    }

    public void setNeedForceTest(boolean needForceTest) {
        this.needForceTest = needForceTest;
    }
}
