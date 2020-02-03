package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.mapper.wx.WxDictDataMapper;
import com.weiziplus.springboot.mapper.wx.WxDictRefMapper;
import com.weiziplus.springboot.models.dto.WxDictRefDto;
import com.weiziplus.springboot.models.wx.WxDictRef;
import com.weiziplus.springboot.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:31
 */
@Service
public class WxDictRefService {

    @Autowired
    WxDictRefMapper wxDictRefMapper;

    @Autowired
    WxDictDataMapper wxDictDataMapper;

    public ResultUtils addDictRef(WxDictRef data) {
        return ResultUtils.success(wxDictRefMapper.insert(data));
    }

    public ResultUtils<List<WxDictRefDto>> getDictDataByDictId(WxDictRef data) {

        List<WxDictRefDto> result = new ArrayList<>();
        List<WxDictRef> list=wxDictRefMapper.getDictDataByDictId(data);
        WxDictRefDto dto=null;
        for(WxDictRef entity:list){
            dto= new WxDictRefDto();
            BeanUtils.copyProperties(entity,dto);
            dto.setWxDictData(wxDictDataMapper.selectByPrimaryKey(entity.getDataId()));
            result.add(dto);
        }
        return ResultUtils.success(result);
    }


}
