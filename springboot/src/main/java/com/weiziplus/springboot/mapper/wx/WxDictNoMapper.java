package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxDictNo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxDictNoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxDictNo record);

    int insertSelective(WxDictNo record);

    WxDictNo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxDictNo record);

    int updateByPrimaryKey(WxDictNo record);
}