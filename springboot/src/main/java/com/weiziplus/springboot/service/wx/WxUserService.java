package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.mapper.wx.WxUserMapper;
import com.weiziplus.springboot.models.wx.WxUser;
import com.weiziplus.springboot.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:31
 */
@Service
public class WxUserService {

    @Autowired
    WxUserMapper wxUserMapper;

    public ResultUtils addWxUser(WxUser data) {
        Integer ret =wxUserMapper.insert(data);
        return ResultUtils.success(data.getId());
    }





}
