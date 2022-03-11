package com.example.demo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * (Vip)表实体类
 *
 * @author ldf
 * @since 2021-07-31 17:29:00
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "vip")
public class Vip extends Model<Vip> {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  @Excel(name="用户名")
  private String vipname;
  @Excel(name="创建时间")
  private Date createTime;
  @Excel(name="联系方式")
  private String phone;
  @Excel(name="购买数量")
  private Integer buynum;
  @Excel(name="地址")
  private String address;
  private Integer userid;
  @TableField(exist = false)
  private ArrayList userInfo;
  @TableField(exist = false)
  private Integer total;

  /**
   * 获取主键值
   *
   * @return 主键值
   */
  @Override
  public Serializable pkVal() {
    return this.id;
  }

    public void add(Vip vipObj) {

    }
}
