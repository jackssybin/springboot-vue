package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.base.BaseService;
import com.weiziplus.springboot.mapper.wx.WxTestQuestionMapper;
import com.weiziplus.springboot.models.wx.WxTestQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/27 16:53
 */
@Service
@Slf4j
public class WxTestQuestionService extends BaseService {

    @Autowired
    private WxTestQuestionMapper wxTestQuestionMapper;

    public void batchInsertList(List<WxTestQuestion> questions){
        this.baseInsertList(questions);
    }

    public List<WxTestQuestion> getQuestionList(Long dicId){
        WxTestQuestion testQuestion = new WxTestQuestion();
        testQuestion.setId(dicId);
        return this.wxTestQuestionMapper.selectByList(testQuestion);
    }

    public void deleteQuestionsByDicId(Long dicId){
        wxTestQuestionMapper.deleteByDicId(dicId);
    }


}
