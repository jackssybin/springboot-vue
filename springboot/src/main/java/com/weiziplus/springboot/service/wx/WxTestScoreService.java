package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.base.BaseService;
import com.weiziplus.springboot.enums.ErrorCodeEnum;
import com.weiziplus.springboot.mapper.wx.WxTestAnswerMapper;
import com.weiziplus.springboot.mapper.wx.WxTestQuestionMapper;
import com.weiziplus.springboot.mapper.wx.WxTestScoreMapper;
import com.weiziplus.springboot.models.wx.WxTestAnswer;
import com.weiziplus.springboot.models.wx.WxTestQuestion;
import com.weiziplus.springboot.models.wx.WxTestScore;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${socreGraceA:太棒了，你是妈妈的骄傲，爱你哦！！！}")
    private String  socreGraceA;

    @Value("${socreGraceB:你已经很优秀了,加油！！！}")
    private String  socreGraceB;

    @Value("${socreGraceC:进步很大，你会更棒的！！！}")
    private String  socreGraceC;

    @Value("${socreGraceD:不要气馁，付出总会有回报的，好好背单词！！！}")
    private String  socreGraceD;

    /**
     * 根据词典id 查询测验结果
     * @param dicId
     */
    public ResultUtils<WxTestScore> checkTestResult(Long dicId){
        log.info("checkTestResult:{}",dicId);
        WxTestScore wxTestScore = wxTestScoreMapper.selectByDicId(dicId);
        if(null==wxTestScore){
            wxTestScore = new WxTestScore();
            wxTestScore.setDicId(dicId);
            WxTestAnswer answer_query  = new WxTestAnswer();
            answer_query.setDicId(dicId);
            List<WxTestAnswer> answerList =wxTestAnswerMapper.selecList(answer_query);
            WxTestQuestion testQuestion = new WxTestQuestion();
            testQuestion.setDicId(dicId);
            List<WxTestQuestion> questList=wxTestQuestionMapper.selectByList(testQuestion);

            WxTestQuestion question = new WxTestQuestion();
            int rightNum=0;
            int score =0;
            //答案数量和问题数量需要一致
            if(null!=answerList&&answerList.size()>0){
                if(answerList.size()==questList.size()){
                    wxTestScore.setOpenid(answerList.get(0).getOpenid());
                    for(WxTestAnswer answer: answerList){
                        question=wxTestQuestionMapper.selectByPrimaryKey(answer.getQuestionId());
                        if(question.getRightAnswer().equalsIgnoreCase(answer.getAnswer())){
                            score=wxTestScore.getScore();
                            score=score+question.getScore();
                            rightNum++;
                            wxTestScore.setScore(score);
                        }
                    }
                    if(answerList.size()==rightNum&&rightNum!=0){
                        wxTestScore.setScore(100);
                    }
                    wxTestScore.setCreateTime(DateUtils.getNowDateTime());
                    wxTestScore.setRemark("done");
                    graceFromScore(wxTestScore);
                    wxTestScoreMapper.insert(wxTestScore);
                }else{
                    wxTestScore = new WxTestScore();
                    wxTestScore.setRemark("unfinish");

                    log.info("测试还没有收集完数据:{}",dicId);
                    return ResultUtils.baseError(ErrorCodeEnum.ERROR_UNFINISH_SCORE.getCode(),String.valueOf(answerList.size()));
                }


            }else{
                log.info("测试还没有收集完数据:{}",dicId);
                wxTestScore = new WxTestScore();
                wxTestScore.setRemark("zero");
                return ResultUtils.baseError(ErrorCodeEnum.ERROR_ZERO_SCORE.getCode(),ErrorCodeEnum.ERROR_ZERO_SCORE.getMsg());
            }

        }
        return ResultUtils.success(wxTestScore) ;
    }


    public void reTesting(Long dicId){
        WxTestAnswer answer = new WxTestAnswer();
        answer.setDicId(dicId);
        wxTestAnswerMapper.deleteByDicIdAndBatchTime(answer);

        wxTestScoreMapper.deleteByDicId(dicId);
    }

    public void graceFromScore(WxTestScore score){
        if(score.getScore()==100){
            score.setAnswer(socreGraceA);
        }else if(score.getScore()>85){
            score.setAnswer(socreGraceB);
        }else if(score.getScore()>60){
            score.setAnswer(socreGraceC);
        }else{
            score.setAnswer(socreGraceD);
        }
    }

}
