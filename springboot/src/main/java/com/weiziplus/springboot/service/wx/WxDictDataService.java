package com.weiziplus.springboot.service.wx;

import com.weiziplus.springboot.mapper.wx.WxDictDataMapper;
import com.weiziplus.springboot.models.vo.WxDictDataVo;
import com.weiziplus.springboot.models.wx.WxDictData;
import com.weiziplus.springboot.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author zhoubin
 * @company:北京千丁互联科技有限公司
 * @date 2020/1/31 13:31
 */
@Service
public class WxDictDataService {

    @Autowired
    WxDictDataMapper wxDictDataMapper;

    public ResultUtils addDictData(WxDictDataVo vo) {
        WxDictData data = new WxDictData();
        BeanUtils.copyProperties(vo,data);
        Integer ret =wxDictDataMapper.insert(data);
        return ResultUtils.success(data.getId());
    }

    public Long judgeDictDataExist(String content){
        WxDictData data = new WxDictData();
        data.setContent(content);
        List<WxDictData> list =wxDictDataMapper.getWxDictDataByContent(data);
        if(null!=list&&list.size()>0){
            return list.get(0).getId();
        }
        return 0L;
    }



}
