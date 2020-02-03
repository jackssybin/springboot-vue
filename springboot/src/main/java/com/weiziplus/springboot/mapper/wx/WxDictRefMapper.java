package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.dto.WxDictRefDto;
import com.weiziplus.springboot.models.wx.WxDictRef;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WxDictRefMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxDictRef record);

    int insertSelective(WxDictRef record);

    WxDictRef selectByPrimaryKey(Long id);

    List<WxDictRef> getDictDataByDictId(WxDictRef record);

    int updateByPrimaryKeySelective(WxDictRef record);

    int updateByPrimaryKey(WxDictRef record);
}