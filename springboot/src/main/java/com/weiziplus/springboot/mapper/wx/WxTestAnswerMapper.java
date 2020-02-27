package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxTestAnswer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WxTestAnswerMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByDicIdAndBatchTime(WxTestAnswer record);

    int insert(WxTestAnswer record);

    int insertSelective(WxTestAnswer record);

    WxTestAnswer selectByPrimaryKey(Long id);

    List<WxTestAnswer> selecList(WxTestAnswer record);

    int updateByPrimaryKeySelective(WxTestAnswer record);

    int updateByPrimaryKey(WxTestAnswer record);
}