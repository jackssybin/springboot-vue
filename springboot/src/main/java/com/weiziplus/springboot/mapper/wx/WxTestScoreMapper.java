package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxTestScore;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxTestScoreMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByDicId(Long dicId);

    int insert(WxTestScore record);

    int insertSelective(WxTestScore record);

    WxTestScore selectByPrimaryKey(Long id);

    WxTestScore selectByDicId(Long id);

    int updateByPrimaryKeySelective(WxTestScore record);

    int updateByPrimaryKey(WxTestScore record);
}