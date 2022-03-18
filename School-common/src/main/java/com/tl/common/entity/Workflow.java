package com.tl.common.entity;

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
 * @since 2021-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Workflow对象", description="")
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "外键，操作人user的账号")
    private String userId;

    @ApiModelProperty(value = "外键，商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "0-上架 1-下架 2-创建待审核 3-创建通过 4-创建被驳回(reject存储驳回信息) 5-修改待审核 6-修改通过 7-修改驳回 8-删除商品")
    private Integer state;

    @ApiModelProperty(value = "工作流内容流程")
    private String content;

    @ApiModelProperty(value = "驳回时存储驳回信息")
    private String reject;


}
