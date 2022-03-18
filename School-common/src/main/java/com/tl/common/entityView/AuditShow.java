package com.tl.common.entityView;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

//审计视图类构建
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AuditShow", description="")
public class AuditShow implements Serializable {
    //操作人姓名
    private String people;
    //操作时间
    private String time;
    //操作内容
    private String content;

    @Override
    public String toString() {
        return "AuditShow{" +
                "people='" + people + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
