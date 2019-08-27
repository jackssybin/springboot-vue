package com.weiziplus.springboot.service.system;

import com.github.pagehelper.PageHelper;
import com.weiziplus.springboot.util.PageUtils;
import com.weiziplus.springboot.mapper.system.SysLogMapper;
import com.weiziplus.springboot.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanglongwei
 * @data 2019/5/13 15:34
 */
@Service
public class SysLogService {
    @Autowired
    SysLogMapper mapper;

    /**
     * 获取日志列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultUtils getPageList(Integer pageNum, Integer pageSize, String username, Long roleId,
                                   String description, String ipAddress, String createTime) {
        PageHelper.startPage(pageNum, pageSize);
        PageUtils pageUtil = PageUtils.pageInfo(mapper.getLogList(username, roleId, description, ipAddress, createTime));
        return ResultUtils.success(pageUtil);
    }
}
