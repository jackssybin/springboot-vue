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
 * wx_dict
 * @author jackssy
 * @date 2020-01-31 16:16:20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_dict")
@Accessors(chain = true)
@Alias("WxDict")
public class WxDict implements Serializable {
    /**
     * 用户表主键，自增
     */
    @ApiModelProperty("用户表主键，自增")
    @Id("id")
    private Long id;

    /**
     * 词典名称
     */
    @ApiModelProperty("词典名称")
    @Column("dic_name")
    private String dicName;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    @Column("dic_type")
    private String dicType;

    /**
     * 创建
     */
    @ApiModelProperty("创建")
    @Column("openid")
    private String openid;

    /**
     * 创建
     */
    @ApiModelProperty("创建")
    @Column("user_id")
    private String userId;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    @Column("create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;
}