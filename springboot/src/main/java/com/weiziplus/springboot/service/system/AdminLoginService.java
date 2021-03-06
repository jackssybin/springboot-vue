package com.weiziplus.springboot.service.system;

import com.weiziplus.springboot.models.SysRole;
import com.weiziplus.springboot.models.SysUser;
import com.weiziplus.springboot.util.*;
import com.weiziplus.springboot.util.redis.RedisUtils;
import com.weiziplus.springboot.util.token.AdminTokenUtils;
import com.weiziplus.springboot.util.token.JwtTokenUtils;
import com.weiziplus.springboot.mapper.system.SysFunctionMapper;
import com.weiziplus.springboot.mapper.system.SysRoleMapper;
import com.weiziplus.springboot.mapper.system.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanglongwei
 * @date 2019/5/9 11:08
 */
@Slf4j
@Service
public class AdminLoginService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysFunctionMapper sysFunctionMapper;

    @Autowired
    SysFunctionService sysFunctionService;

    /**
     * 图片验证码放入session中的key
     */
    private final String LOGIN_RANDOM_CODE = "loginRandomCoe";

    private final String REDIS_KEY = "service:system:AdminLoginService:loginRandomCode:";

    /**
     * 生成图片验证码
     *
     * @param response
     * @return
     */
    public void getValidateCode(HttpServletResponse response, String uuid) {
        if (ToolUtils.isBlank(uuid)) {
            return;
        }
        Map<String, Object> validateCode = ImageValidateCodeUtils.getValidateCode();
        String redisKey = REDIS_KEY + uuid;
        RedisUtils.set(redisKey, validateCode.get("random"), 60L);
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            ImageIO.write((BufferedImage) validateCode.get("image"), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            log.warn("登录时生成图片验证码失败" + e);
        }
    }

    /**
     * 系统用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public ResultUtils login(HttpServletRequest request, String username, String password, String code, String uuid) {
        if (ToolUtils.isBlank(username) || ToolUtils.isBlank(password)) {
            return ResultUtils.error("用户名或密码为空");
        }
        if (ToolUtils.isBlank(code) || ImageValidateCodeUtils.RANDOM_NUM != code.length()) {
            return ResultUtils.error("验证码错误");
        }
        if (ToolUtils.isBlank(uuid)) {
            return ResultUtils.error("uuid为空");
        }
        String redisKey = REDIS_KEY + uuid;
        String randomCode = ToolUtils.valueOfString(RedisUtils.get(redisKey));
        if (null == randomCode) {
            return ResultUtils.error("验证码已过期");
        }
        if (!code.equalsIgnoreCase(randomCode)) {
            RedisUtils.delete(redisKey);
            return ResultUtils.error("验证码错误");
        }
        SysUser sysUser = sysUserMapper.getInfoByUsername(username);
        if (null == sysUser || !sysUser.getPassword().equals(Md5Utils.encode(password))) {
            return ResultUtils.error("用户名或密码错误");
        }
        if (SysUserService.ADMIN_USER_ALLOW_LOGIN_ONE.equals(sysUser.getAllowLogin())) {
            return ResultUtils.error("账号被禁用，请联系管理员");
        }
        if (SysUserService.ADMIN_USER_ALLOW_LOGIN_TWO.equals(sysUser.getAllowLogin())) {
            return ResultUtils.error("账号封号中，请联系管理员");
        }
        SysRole sysRole = sysRoleMapper.getInfoByUserId(sysUser.getId());
        if (null == sysRole) {
            return ResultUtils.error("您还没有角色，请联系管理员添加");
        }
        if (!SysRoleService.ADMIN_ROLE_IS_STOP_ZERO.equals(sysRole.getIsStop())) {
            return ResultUtils.error("角色被禁用，请联系管理员");
        }
        String token = AdminTokenUtils.createToken(sysUser.getId(), HttpRequestUtils.getIpAddress(request), sysRole.getId());
        if (null == token) {
            return ResultUtils.error("登录失败，请重试");
        }
        sysUser.setPassword(null);
        Map<String, Object> map = new HashMap<>(5);
        map.put("token", token);
        map.put("userInfo", sysUser);
        map.put("role", sysRole.getName());
        map.put("routerTree", sysFunctionService.getMenuTreeByRoleId(sysRole.getId()));
        map.put("roleButtons", sysFunctionMapper.getButtonListByRoleId(sysRole.getId()));
        return ResultUtils.success(map);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    public ResultUtils logout(HttpServletRequest request) {
        Long userId = JwtTokenUtils.getUserIdByHttpServletRequest(request);
        AdminTokenUtils.deleteToken(userId);
        return ResultUtils.success();
    }
}
