package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.models.wx.WxDictRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface WxDictDataMapper {
    int deleteByPrimaryKey(Long id);

    Integer insert(WxDictData record);

    Integer insertSelective(WxDictData record);

    WxDictData selectByPrimaryKey(Long id);

    List<WxDictData> getWxDictDataByContent(WxDictData data);

    int updateByPrimaryKeySelective(WxDictData record);

    int updateByPrimaryKey(WxDictData record);
}