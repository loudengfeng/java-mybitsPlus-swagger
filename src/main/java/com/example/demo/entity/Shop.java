package com.example.demo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Shop)表实体类
 *
 * @author ldf
 * @since 2022-02-22 16:09:43
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "shop")
public class Shop extends Model<Shop> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String shoptitle;
    private Double price;
    private Integer shopnum;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
