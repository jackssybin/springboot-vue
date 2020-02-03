package com.weiziplus.springboot.models.wx;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.weiziplus.springboot.base.Column;
import com.weiziplus.springboot.base.Id;
import com.weiziplus.springboot.base.Table;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * wx_dict_ref
 * @author jackssy
 * @date 2020-01-31 16:27:40
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_dict_ref")
@Accessors(chain = true)
@Alias("WxDictRef")
public class WxDictRef implements Serializable {
    /**
     * 用户表主键，自增
     */
    @ApiModelProperty("用户表主键，自增")
    @Id("id")
    private Long id;

    /**
     * 词典id
     */
    @ApiModelProperty("词典id")
    @Column("dic_id")
    private Long dicId;

    /**
     * 单词id
     */
    @ApiModelProperty("单词id")
    @Column("data_id")
    private Long dataId;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    @Column("create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;
}