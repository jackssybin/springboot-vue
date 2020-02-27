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
 * wx_test_answer
 * @author Administrator
 * @date 2020-02-23 22:13:46
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_test_answer")
@Accessors(chain = true)
@Alias("WxTestAnswer")
public class WxTestAnswer implements Serializable {
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
     * 问题选项
     */
    @ApiModelProperty("问题选项")
    @Column("question_id")
    private Long questionId;

    /**
     * openid
     */
    @ApiModelProperty("openid")
    @Column("openid")
    private String openid;

    /**
     * 用户答案
     */
    @ApiModelProperty("用户答案")
    @Column("answer")
    private String answer;

    /**
     * 测验的唯一标记，同一份测验可以多次考试，点击开始答题时生成测验唯一编码
     */
    @ApiModelProperty("测验的唯一标记，同一份测验可以多次考试，点击开始答题时生成测验唯一编码")
    @Column("batch_time")
    private String batchTime;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    @Column("create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;
}