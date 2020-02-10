package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxDictRef;
import com.weiziplus.springboot.models.wx.WxUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WxUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(Long id);

    List<WxUser> getWxUserByContent(WxUser record);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}