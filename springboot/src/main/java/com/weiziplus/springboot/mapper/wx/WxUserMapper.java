package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}