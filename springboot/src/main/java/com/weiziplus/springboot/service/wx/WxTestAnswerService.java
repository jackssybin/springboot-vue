package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.base.BaseService;
import com.weiziplus.springboot.mapper.wx.WxTestAnswerMapper;
import com.weiziplus.springboot.models.wx.WxTestAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/27 17:27
 */
@Service
@Slf4j
public class WxTestAnswerService extends BaseService {

    @Autowired
    private WxTestAnswerMapper wxTestAnswerMapper;

    public int insert(WxTestAnswer record){
        wxTestAnswerMapper.deleteByDicIdAndBatchTime(record);
        return wxTestAnswerMapper.insert(record);
    }
}
