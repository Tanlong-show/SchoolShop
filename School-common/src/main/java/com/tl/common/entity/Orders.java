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
 * @since 2021-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Oder对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id，主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单编号")
    private String ordNumber;

    @ApiModelProperty(value = "外键，商品的主键id")
    private Integer goodsId;

    @ApiModelProperty(value = "订单创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", ordNumber='" + ordNumber + '\'' +
                ", goodsId=" + goodsId +
                ", createTime=" + createTime +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                ", state=" + state +
                '}';
    }
}
