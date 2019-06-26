package com.weiziplus.springboot.service.user;

import com.weiziplus.springboot.mapper.user.UserMapper;
import com.weiziplus.springboot.utils.ResultUtil;
import com.weiziplus.springboot.utils.token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wanglongwei
 * @data 2019/5/10 17:10
 */
@Service
public class UserService {
    @Autowired
    UserMapper mapper;

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    public ResultUtil getInfo(HttpServletRequest request) {
        Long userId = JwtTokenUtil.getUserIdByHttpServletRequest(request);
        return ResultUtil.success(mapper.getUserInfoByUserId(userId));
    }
}