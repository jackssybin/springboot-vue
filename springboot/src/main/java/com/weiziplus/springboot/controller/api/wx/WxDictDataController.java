package com.weiziplus.springboot.controller.api.wx;

import com.weiziplus.springboot.interceptor.AuthTokenIgnore;
import com.weiziplus.springboot.models.dto.WxDictRefDto;
import com.weiziplus.springboot.models.vo.WxDictDataVo;
import com.weiziplus.springboot.models.wx.WxDict;
import com.weiziplus.springboot.models.wx.WxDictRef;
import com.weiziplus.springboot.service.wx.WxDictDataService;
import com.weiziplus.springboot.service.wx.WxDictRefService;
import com.weiziplus.springboot.service.wx.WxDictService;
import com.weiziplus.springboot.util.DateUtils;
import com.weiziplus.springboot.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:33
 */
@RestController
@RequestMapping("/api/wx")
@Api(tags = "词典接口")
@Slf4j
public class WxDictDataController {

    @Autowired
    WxDictDataService wxDictDataService;

    @Autowired
    WxDictService wxDictService;

    @Autowired
    WxDictRefService wxDictRefService ;

    @ApiOperation(value = "新增词典")
    @PostMapping("/addDict")
    @AuthTokenIgnore
    public ResultUtils addDict(@RequestBody WxDict data)
    {

        log.info("addDict:{}",data);
        data.setCreateTime(DateUtils.getNowDateTime());
        data.setDicType("wx");
        return wxDictService.addDict(data);
    }

    @ApiOperation(value = "新增单词")
    @PostMapping("/addDictData")
    @AuthTokenIgnore
    public ResultUtils addDictData(@RequestBody WxDictDataVo data)
    {
        log.info("addDictData:{}",data);
        if(data.getDicId() ==0L){
            return ResultUtils.error("不能向本地词库添加单词");
        }else{
            data.setCreateTime(DateUtils.getNowDateTime());
            Long dataId=wxDictDataService.judgeDictDataExist(data.getContent());
            if(dataId==0L){
                ResultUtils addResp=wxDictDataService.addDictData(data);
                dataId = (Long)addResp.getData();
                log.info("result data:{}",data);
            }
            WxDictRef ref = new WxDictRef();
            //默认初始化的一个词库
            ref.setDicId(data.getDicId());
            ref.setDataId(dataId);
            ref.setCreateTime(DateUtils.getNowDateTime());
            wxDictRefService.addDictRef(ref);
        }


        return ResultUtils.success();
    }

    @ApiOperation(value = "新增单词词典关系")
    @PostMapping("/addDictRef")
    @AuthTokenIgnore
    public ResultUtils addDictRef(@RequestBody WxDictRef data)
    {
        log.info("addDictRef:{}",data);
        data.setCreateTime(DateUtils.getNowDateTime());
        return wxDictRefService.addDictRef(data);
    }

    @ApiOperation(value = "获取词典单词")
    @GetMapping("/getDictData")
    @AuthTokenIgnore
    public ResultUtils<List<WxDictRefDto>> getDictData(WxDictRef data)
    {
        log.info("getDictData:{}",data);
        return wxDictRefService.getDictDataByDictId(data);
    }

    @ApiOperation(value = "获取词典列表")
    @GetMapping("/getDictList")
    @AuthTokenIgnore
    public ResultUtils<List<WxDict>> getDictList(WxDict data)
    {
        log.info("getDictList:{}",data);
        return wxDictService.getList(data);
    }

}
