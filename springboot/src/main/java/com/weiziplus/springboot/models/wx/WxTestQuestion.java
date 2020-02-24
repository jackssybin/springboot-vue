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
 * wx_test_question
 * @author Administrator
 * @date 2020-02-23 22:17:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_test_question")
@Accessors(chain = true)
@Alias("WxTestQuestion")
public class WxTestQuestion implements Serializable {
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
     * 分数
     */
    @ApiModelProperty("分数")
    @Column("score")
    private Integer score;

    /**
     * 问题
     */
    @ApiModelProperty("问题")
    @Column("question")
    private String question;

    /**
     * 选项A
     */
    @ApiModelProperty("选项A")
    @Column("choiceA")
    private String choicea;

    /**
     * 选项B
     */
    @ApiModelProperty("选项B")
    @Column("choiceB")
    private String choiceb;

    /**
     * 选项C
     */
    @ApiModelProperty("选项C")
    @Column("choiceC")
    private String choicec;

    /**
     * 选项D
     */
    @ApiModelProperty("选项D")
    @Column("choiceD")
    private String choiced;

    /**
     * 正确答案
     */
    @ApiModelProperty("正确答案")
    @Column("right_answer")
    private String rightAnswer;

    /**
     * 排序数字
     */
    @ApiModelProperty("排序数字")
    @Column("order_number")
    private Integer orderNumber;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    @Column("create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;
}