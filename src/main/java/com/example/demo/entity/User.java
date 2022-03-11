package com.example.demo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author ldf
 * @since 2021-07-31 17:00:50
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "user")
public class User extends Model<User> {
  @ApiModelProperty(value = "用户名")
  private String username;
  @ApiModelProperty(value = "年龄")
  private Integer age;
  @ApiModelProperty(value = "创建时间")
  private Object create_time;
  @ApiModelProperty(value = "性别")
  private String six;
  @ApiModelProperty(value = "用户关联的id")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  @ApiModelProperty(value = "账户名")
  private Integer phone;
  @ApiModelProperty(value = "密码")
  private String psd;


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
