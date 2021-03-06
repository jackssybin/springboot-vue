package com.weiziplus.springboot.controller.pc.data.dictionary;

import com.weiziplus.springboot.interceptor.AdminAuthToken;
import com.weiziplus.springboot.interceptor.SystemLog;
import com.weiziplus.springboot.service.data.dictionary.DataDictionaryIpFilterService;
import com.weiziplus.springboot.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wanglongwei
 * @date 2019/8/5 10:17
 */
@RestController
@ApiIgnore
@AdminAuthToken
@RequestMapping("/pc/dataDictionary/ipFilter")
public class DataDictionaryIpFilterController {

    @Autowired
    DataDictionaryIpFilterService service;

    /**
     * 获取ip名单分页列表
     *
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    @GetMapping("/getPageList")
    @SystemLog(description = "查看ip名单列表")
    public ResultUtils getPageList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "type", defaultValue = "0") Integer type) {
        return service.getPageList(pageNum, pageSize, type);
    }

    /**
     * 新增ip名单
     *
     * @param request
     * @param ip
     * @param type
     * @return
     */
    @PostMapping("/add")
    @SystemLog(description = "新增ip名单")
    public ResultUtils add(HttpServletRequest request, String ip, Integer type) {
        return service.add(request, ip, type);
    }

    /**
     * 名单删除ip
     *
     * @param request
     * @param id
     * @return
     */
    @SystemLog(description = "删除ip名单")
    @PostMapping("/delete")
    public ResultUtils delete(HttpServletRequest request, Long id) {
        return service.delete(request, id);
    }
}
