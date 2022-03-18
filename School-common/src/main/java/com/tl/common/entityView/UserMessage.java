package com.tl.common.entityView;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//与我相关的订单消息，我的商铺消息
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="我的商铺消息", description="")
public class UserMessage implements Serializable {
    //订单编号
    String OrdNumber;
    //购买者编号学号
    Integer buyerId;
    //购买者主键id
    Integer buyerMainId;
    //商品id
    Integer goodId;
    //购买者名称
    private String buyerName;
    //消息产生时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    //商品名称
    private String goodName;

    //0-未处理=下单了 1-已处理=支付了  2-被卖家取消=取消了
    private String type;
    //头像
    private String headpic;

}
