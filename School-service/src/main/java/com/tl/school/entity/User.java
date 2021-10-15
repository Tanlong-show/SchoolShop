package com.tl.school.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author tl
 * @since 2021-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "性别  0-女 1-男")
    private Integer sex;

    @ApiModelProperty(value = "信誉积分")
    private Integer grade;

    @ApiModelProperty(value = "身份权限 0-普通用户 1-管理员 ")
    private Integer root;

    @ApiModelProperty(value = "在线状态 0-不在线 1-在线")
    private Integer status;

    @ApiModelProperty(value = "登录账号")
    private String userId;

    @ApiModelProperty(value = "登陆密码")
    private String password;

    @ApiModelProperty(value = "头像图片路径")
    private String headpicture;

    @ApiModelProperty(value = "认证状态 0-未认证 1-已认证")
    private Integer authentic;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
