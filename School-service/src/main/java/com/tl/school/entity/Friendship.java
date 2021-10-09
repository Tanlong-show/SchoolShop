package com.tl.school.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Friendship对象", description="")
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer friendId;

    @ApiModelProperty(value = "设置当前状态，申请添加好友；0-未通过 1-通过")
    private Integer status;


}
