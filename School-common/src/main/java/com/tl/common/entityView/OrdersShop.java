package com.tl.common.entityView;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.common.entity.Goods;
import com.tl.common.entity.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author tl
 * @since 2021-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OderShop视图对象", description="")
public class OrdersShop implements Serializable {
    @ApiModelProperty(value = "订单编号")
    private String ordNumber;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "图片路径")
    private String pictureUrl;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "外键，买方id")
    private Integer buyerId;

    @ApiModelProperty(value = "外键，卖方id")
    private Integer sellerId;

    @ApiModelProperty(value = "订单状态：0-未处理 1-已处理  2-被卖家取消")
    private Integer state;

}