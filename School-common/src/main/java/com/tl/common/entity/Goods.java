package com.tl.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
@ApiModel(value="Goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "一级分类")
    private String sortone;

    @ApiModelProperty(value = "二级分类")
    private String sorttwo;

    @ApiModelProperty(value = "商品库存")
    private Integer number;

    @ApiModelProperty(value = "交易地点")
    private String address;

    @ApiModelProperty(value = "图片路径")
    private String pictureUrl;

    @ApiModelProperty(value = "视频路径")
    private String videoUrl;

    @ApiModelProperty(value = "0-未上架 1-已上架 2-创建审核中 3-修改审核中 4-审核未通过")
    private Integer state;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "外键关联user的id")
    private Integer userId;
}
