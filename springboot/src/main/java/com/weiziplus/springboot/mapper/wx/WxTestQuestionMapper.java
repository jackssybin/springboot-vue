package com.weiziplus.springboot.mapper.wx;

import com.weiziplus.springboot.models.wx.WxTestQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WxTestQuestionMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByDicId(Long dicId);



    int insert(WxTestQuestion record);

    int insertSelective(WxTestQuestion record);

    WxTestQuestion selectByPrimaryKey(Long id);

    List<WxTestQuestion> selectByList(WxTestQuestion record);

    int updateByPrimaryKeySelective(WxTestQuestion record);

    int updateByPrimaryKey(WxTestQuestion record);
}