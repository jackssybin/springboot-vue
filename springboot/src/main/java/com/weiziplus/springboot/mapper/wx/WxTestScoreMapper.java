package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxTestScore;

public interface WxTestScoreMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxTestScore record);

    int insertSelective(WxTestScore record);

    WxTestScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxTestScore record);

    int updateByPrimaryKey(WxTestScore record);
}