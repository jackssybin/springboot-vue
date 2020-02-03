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
 * wx_user
 * @author jackssy
 * @date 2020-02-01 20:01:45
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Table("wx_user")
@Accessors(chain = true)
@Alias("WxUser")
public class WxUser implements Serializable {
    /**
     * 用户表主键，自增
     */
    @ApiModelProperty("用户表主键，自增")
    @Id("id")
    private Long id;

    /**
     */
    @Column("phone_num")
    private String phoneNum;

    /**
     * wx_id
     */
    @ApiModelProperty("wx_id")
    @Column("unionid")
    private String unionid;

    /**
     * nickName
     */
    @ApiModelProperty("nickName")
    @Column("nick_name")
    private String nickName;

    /**
     * avatarUrl
     */
    @ApiModelProperty("avatarUrl")
    @Column("avatar_url")
    private String avatarUrl;

    /**
     * 性别 0：未知、1：男、2：女 
     */
    @ApiModelProperty("性别 0：未知、1：男、2：女 ")
    @Column("gender")
    private String gender;

    /**
     * 省市
     */
    @ApiModelProperty("省市")
    @Column("province")
    private String province;

    /**
     * city
     */
    @ApiModelProperty("city")
    @Column("city")
    private String city;

    /**
     * country
     */
    @ApiModelProperty("country")
    @Column("country")
    private String country;

    /**
     * 用户创建时间
     */
    @ApiModelProperty("用户创建时间")
    @Column("create_time")
    private String createTime;

    private static final long serialVersionUID = 1L;
}