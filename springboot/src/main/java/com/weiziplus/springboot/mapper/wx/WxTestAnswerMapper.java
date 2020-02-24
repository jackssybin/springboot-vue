package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxTestAnswer;

public interface WxTestAnswerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxTestAnswer record);

    int insertSelective(WxTestAnswer record);

    WxTestAnswer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxTestAnswer record);

    int updateByPrimaryKey(WxTestAnswer record);
}