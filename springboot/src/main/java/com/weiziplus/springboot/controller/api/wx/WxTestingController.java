package com.weiziplus.springboot.controller.api.wx;

import com.weiziplus.springboot.interceptor.AuthTokenIgnore;
import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.models.wx.WxTestAnswer;
import com.weiziplus.springboot.models.wx.WxTestQuestion;
import com.weiziplus.springboot.models.wx.WxTestScore;
import com.weiziplus.springboot.models.wx.dto.WxTestQuestionDto;
import com.weiziplus.springboot.service.wx.*;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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

    @Autowired
    WxTestAnswerService wxTestAnswerService;

    @Autowired
    WxTestScoreService wxTestScoreService;

    @Value("${global.word-list}")
    private String wordList;

    @ApiOperation(value = "初始化单词")
    @GetMapping("/initWord")
    @AuthTokenIgnore
    public ResultUtils initWord()
    {
        log.info("initWord:{}",wordList);
        String[] words =wordList.split(",");
        log.info("words:{}",words);
        new Thread(()-> {
            for(String word :words){
                try {
                    wxDictDataService.queryInsertWord(word);
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();

        return ResultUtils.success();
    }

    @ApiOperation(value = "获取随机单词")
    @GetMapping("/getNumbers")
    @AuthTokenIgnore
    public ResultUtils<List<WxDictData>> getRandomByNumbers(int numbers)
    {
        return ResultUtils.success(this.wxDictDataService.getRandomByNumbers(numbers));
    }

    @ApiOperation(value = "开始测验")
    @PostMapping("/startTest")
    @AuthTokenIgnore
    public ResultUtils<List<WxTestQuestion>> startTest(@RequestBody WxTestQuestionDto question)
    {
        log.info("startTest param:{}",question);
        Long dicId =question.getDicId();
        ResultUtils<List<WxTestQuestion>> optionsList =wxDictDataService.getTestingList(dicId,question.isNeedForceTest());
        log.info("startTest optionsList:{}",optionsList);

        return optionsList;
    }


    @ApiOperation(value = "提交测验结果")
    @PostMapping("/submitTest")
    @AuthTokenIgnore
    public ResultUtils<Integer> submitTest(@RequestBody WxTestAnswer answer)
    {
        log.info("submitTest param:{}",answer);
        answer.setCreateTime(DateUtils.getNowDateTime());
        int result =wxTestAnswerService.insert(answer);
        log.info("submitTest result:{}",result);
        return ResultUtils.success(result);
    }


    @ApiOperation(value = "查看测验结果")
    @PostMapping("/checkTestResult")
    @AuthTokenIgnore
    public ResultUtils<WxTestScore> checkTestResult(@RequestBody WxTestAnswer answer)
    {
        log.info("checkTestResult param:{}",answer);
        answer.setCreateTime(DateUtils.getNowDateTime());
        WxTestScore result =wxTestScoreService.checkTestResult(answer.getDicId());
        log.info("checkTestResult result:{}",result);
        return ResultUtils.success(result);
    }




}
