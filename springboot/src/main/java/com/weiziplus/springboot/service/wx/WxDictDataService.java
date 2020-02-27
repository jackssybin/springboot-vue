package com.weiziplus.springboot.service.wx;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiziplus.springboot.base.BaseService;
import com.weiziplus.springboot.mapper.wx.WxDictDataMapper;
import com.weiziplus.springboot.models.dto.WxDictRefDto;
import com.weiziplus.springboot.models.vo.WxDictDataVo;
import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.models.wx.WxTestQuestion;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:31
 */
@Service
@Slf4j
public class WxDictDataService extends BaseService {

    @Autowired
    WxDictDataMapper wxDictDataMapper;

    private WxTestQuestionService wxTestQuestionService ;

    @Autowired
    private WxDictRefService wxDictRefService;

    @Autowired
    RestTemplate restTemplate ;

    public static String CHOICE_A="A";
    public static String CHOICE_B="B";
    public static String CHOICE_C="C";
    public static String CHOICE_D="D";

    private String queryWordUrl="https://api.shanbay.com/bdc/search/?word=";

    public static Random random = new Random();


    public ResultUtils addDictData(WxDictDataVo vo) {
        WxDictData data = new WxDictData();
        BeanUtils.copyProperties(vo,data);
        return addDictData(data);
    }
    public ResultUtils addDictData(WxDictData data) {
        Integer ret =wxDictDataMapper.insert(data);
        return ResultUtils.success(data.getId());
    }

    public Long judgeDictDataExist(String content){
        WxDictData data = new WxDictData();
        data.setContent(content);
        List<WxDictData> list =wxDictDataMapper.getWxDictDataByContent(data);
        if(null!=list&&list.size()>0){
            return list.get(0).getId();
        }
        return 0L;
    }



    public String queryInsertWord(String word)
    {
        word=word.replaceAll("'","");
        WxDictData dictData = new WxDictData();
        String result =restTemplate.getForEntity(queryWordUrl+word,String.class).getBody();
        log.info("queryWord result:{}",result);
        try {
            JSONObject ret = JSON.parseObject(result);
            String status_code = ret.getString("status_code");
            if("0".equalsIgnoreCase(status_code)){
                JSONObject data_ret =ret.getJSONObject("data");
                dictData.setContent(data_ret.getString("content"));
                dictData.setAudio(data_ret.getString("audio"));
                dictData.setUkAudio(data_ret.getString("uk_audio"));
                dictData.setPron(data_ret.getJSONObject("pronunciations").getString("us"));
                dictData.setUkPron(data_ret.getJSONObject("pronunciations").getString("uk"));
                dictData.setExample(data_ret.getString("url"));
                dictData.setDefinition(data_ret.getString("definition"));
                dictData.setCreateTime(DateUtils.getNowDateTime());

                Long dataId=judgeDictDataExist(dictData.getContent());
                if(null==dataId||0L==dataId){
                    addDictData(dictData);
                    log.info("插入成功:{}",dictData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long getMaxId(){
        return this.wxDictDataMapper.getMaxId();
    }


    public List<WxDictData> getRandomByNumbers(int rndNum){
        Long maxId =getMaxId();
        Long minId =1000001L;
        Random random  = new Random();
        Set<Long> idSet = new HashSet();
        int temp =0;
        while(temp<=rndNum){
            idSet.add(random.nextInt(maxId.intValue())%(maxId-minId+1) + minId.intValue());
            temp =idSet.size();
        }
        List<WxDictData> list = this.wxDictDataMapper.selectByIdList(new ArrayList<>(idSet));

        return list;
    }


    public ResultUtils<List<WxTestQuestion>> getTestingList(Long dicId,boolean needForceTest)
    {

        List<WxTestQuestion> list = new ArrayList<>();
        List<WxDictRefDto> refList = wxDictRefService.getDictDataByDictId(dicId);
        if(refList.size()<4){
            return ResultUtils.error("测验库数量太少，不能进行测试。请将需要的单词添加进词库");
        }
        list=wxTestQuestionService.getQuestionList(dicId);
        if(needForceTest){
            log.info("删除测验数据:{}",dicId);
            wxTestQuestionService.deleteQuestionsByDicId(dicId);
        }else{
            if(list.size()>0){
                log.info("从缓存拿出历史测验:{}",list);
                return ResultUtils.success(list);
            }
        }
        int testRndNumber =refList.size()*3;
        List<WxDictData> rndList = getRandomByNumbers(testRndNumber);
        WxTestQuestion option = null;
        int rndIndx =0;
        int step = 0;
        int stepLength =4;
        for (WxDictRefDto refDto :refList){
            option = new WxTestQuestion();
            option.setDicId(refDto.getDicId());
            option.setQuestion(refDto.getWxDictData().getContent());
            setRightAnswer(option,refDto);
            while(step<stepLength){
                log.info("step:{},rndIndex:{}",step,rndIndx);
                if(step==0&& StringUtils.isEmpty(option.getChoicea())){
                    option.setChoicea(rndList.get(rndIndx).getDefinition());
                    rndIndx++;
                }
                if(step==1&& StringUtils.isEmpty(option.getChoiceb())){
                    option.setChoiceb(rndList.get(rndIndx).getDefinition());
                    rndIndx++;
                }
                if(step==2&& StringUtils.isEmpty(option.getChoicec())){
                    option.setChoicec(rndList.get(rndIndx).getDefinition());
                    rndIndx++;
                }
                if(step==3&& StringUtils.isEmpty(option.getChoiced())){
                    option.setChoiced(rndList.get(rndIndx).getDefinition());
                    rndIndx++;
                }

                step++;
                if(step==stepLength){
                    step =0;
                    break;
                }


            }
            option.setCreateTime(DateUtils.getNowDateTime());
            option.setScore(getAvgScore(refList));
            option.setOrderNumber(random.nextInt(100));
            list.add(option);
        }

        wxTestQuestionService.batchInsertList(list);
        return  ResultUtils.success(list);
    }

    public Integer getAvgScore(List<WxDictRefDto> refList){
        return 100/refList.size();
    }

    public void setRightAnswer(WxTestQuestion option,WxDictRefDto rightDto){
        String[] choices ={"A","B","C","D"};
        int a= (int) Math.floor(Math.random()*choices.length);
        String choice = choices[a];
        option.setRightAnswer(choice);
        String rightChoice =rightDto.getWxDictData().getDefinition();
        if(CHOICE_A.equalsIgnoreCase(choice)){
            option.setChoicea(rightChoice);
        }else if(CHOICE_B.equalsIgnoreCase(choice)){
            option.setChoiceb(rightChoice);
        }else if(CHOICE_C.equalsIgnoreCase(choice)){
            option.setChoicec(rightChoice);
        }else if(CHOICE_D.equalsIgnoreCase(choice)){
            option.setChoiced(rightChoice);
        }
    }

    public static void main(String[] args) {
        String[] choices ={"A","B","C","D"};
        int a= (int) Math.floor(Math.random()*choices.length);
        String choice = choices[a];
        System.out.println(a);
        System.out.println(choice);

        System.out.println(100/6);
    }


}
