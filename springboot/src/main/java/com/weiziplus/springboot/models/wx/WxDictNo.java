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
 * wx_dict_no
 * @author jackssy
 * @date 2020-02-02 13:12:55
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_dict_no")
@Accessors(chain = true)
@Alias("WxDictNo")
public class WxDictNo implements Serializable {
    /**
     * 用户表主键，自增
     */
    @ApiModelProperty("用户表主键，自增")
    @Id("id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @Column("user_id")
    private String userId;

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