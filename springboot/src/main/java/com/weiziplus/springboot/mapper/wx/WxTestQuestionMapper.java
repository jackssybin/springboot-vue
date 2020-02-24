package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxTestQuestion;

public interface WxTestQuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxTestQuestion record);

    int insertSelective(WxTestQuestion record);

    WxTestQuestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxTestQuestion record);

    int updateByPrimaryKey(WxTestQuestion record);
}