package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxDict;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WxDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxDict record);

    int insertSelective(WxDict record);

    WxDict selectByPrimaryKey(Long id);

    List<WxDict> getList(WxDict record);

    int updateByPrimaryKeySelective(WxDict record);

    int updateByPrimaryKey(WxDict record);
}