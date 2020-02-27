package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.models.wx.WxDictRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface WxDictDataMapper {
    int deleteByPrimaryKey(Long id);

    Integer insert(WxDictData record);

    Integer insertSelective(WxDictData record);

    WxDictData selectByPrimaryKey(Long id);

    List<WxDictData> getWxDictDataByContent(WxDictData data);

    int updateByPrimaryKeySelective(WxDictData record);

    int updateByPrimaryKey(WxDictData record);

    Long getMaxId();

    List<WxDictData> selectByIdList(@Param("idList") List<Long> idList);



}