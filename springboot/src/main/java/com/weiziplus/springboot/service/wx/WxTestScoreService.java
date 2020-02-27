package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.base.BaseService;
import com.weiziplus.springboot.mapper.wx.WxTestAnswerMapper;
import com.weiziplus.springboot.mapper.wx.WxTestQuestionMapper;
import com.weiziplus.springboot.mapper.wx.WxTestScoreMapper;
import com.weiziplus.springboot.models.wx.WxTestAnswer;
import com.weiziplus.springboot.models.wx.WxTestQuestion;
import com.weiziplus.springboot.models.wx.WxTestScore;
import com.weiziplus.springboot.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/2/27 18:12
 */

@Service
@Slf4j
public class WxTestScoreService extends BaseService
{
    @Autowired
    private WxTestScoreMapper wxTestScoreMapper;

    @Autowired
    private WxTestAnswerMapper wxTestAnswerMapper;

    @Autowired
    private WxTestQuestionMapper wxTestQuestionMapper;

    /**
     * 根据词典id 查询测验结果
     * @param dicId
     */
    public WxTestScore checkTestResult(Long dicId){
        log.info("checkTestResult:{}",dicId);
        WxTestScore wxTestScore = wxTestScoreMapper.selectByDicId(dicId);
        if(null==wxTestScore){
            wxTestScore = new WxTestScore();
            wxTestScore.setDicId(dicId);
            WxTestAnswer answer_query  = new WxTestAnswer();
            answer_query.setDicId(dicId);
            List<WxTestAnswer> answerList =wxTestAnswerMapper.selecList(answer_query);
            WxTestQuestion question = new WxTestQuestion();
            int rightNum=0;
            int score =0;
            if(null!=answerList&&answerList.size()>0){
                wxTestScore.setOpenid(answerList.get(0).getOpenid());
                for(WxTestAnswer answer: answerList){
                    question=wxTestQuestionMapper.selectByPrimaryKey(answer.getQuestionId());
                    if(question.getRightAnswer().equalsIgnoreCase(answer.getAnswer())){
                        score=question.getScore();
                        score=score+wxTestScore.getScore();
                        rightNum++;
                        wxTestScore.setScore(score);
                    }
                }
                if(answerList.size()==rightNum&&rightNum!=0){
                    wxTestScore.setScore(100);
                }
                wxTestScore.setCreateTime(DateUtils.getNowDateTime());
                wxTestScoreMapper.insert(wxTestScore);
            }else{
                log.info("测试还没有收集完数据:{}",dicId);
            }

        }
        return wxTestScore ;
    }



}
