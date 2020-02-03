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
 * wx_dict_data
 * @author jackssy
 * @date 2020-01-31 15:49:13
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_dict_data")
@Accessors(chain = true)
@Alias("WxDictData")
public class WxDictData implements Serializable {
    /**
     * 用户表主键，自增
     */
    @ApiModelProperty("用户表主键，自增")
    @Id("id")
    private Long id;

    /**
     * 单词内容
     */
    @ApiModelProperty("单词内容")
    @Column("content")
    private String content;

    /**
     * 发音
     */
    @ApiModelProperty("发音")
    @Column("pron")
    private String pron;

    /**
     * 发音
     */
    @ApiModelProperty("发音")
    @Column("uk_pron")
    private String ukPron;

    /**
     * 美音
     */
    @ApiModelProperty("美音")
    @Column("audio")
    private String audio;

    /**
     * 英音
     */
    @ApiModelProperty("英音")
    @Column("uk_audio")
    private String ukAudio;

    /**
     * 定义
     */
    @ApiModelProperty("定义")
    @Column("definition")
    private String definition;

    /**
     * 例子
     */
    @ApiModelProperty("例子")
    @Column("example")
    private String example;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    @Column("create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;
}