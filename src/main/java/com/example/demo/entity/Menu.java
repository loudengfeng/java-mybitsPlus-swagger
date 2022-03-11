package com.example.demo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (Menu)表实体类
 *
 * @author ldf
 * @since 2021-08-12 11:05:15
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "menu")
public class Menu extends Model<Menu> {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  @ApiModelProperty(value = "父id")
  private String belong;
  @ApiModelProperty(value = "路由地址")
  private String url;
  @ApiModelProperty(value = "创建时间")
  private Date createTime;
  @ApiModelProperty(value = "处理过的时间")
  @TableField(exist = false)
  private String dealTime;
  @ApiModelProperty(value = "路由名称")
  private String menuName;
  @ApiModelProperty(value = "排序")
  private String sort;
  @ApiModelProperty(value = "是否外联 0 外联 1不是")
  private String outreach;
  @ApiModelProperty(value = "组件地址")
  private String component;
  @ApiModelProperty(value = "处理成三级菜单")
  @TableField(exist = false)
  private List childList;
  @ApiModelProperty(value = "菜单展示状态 1正常0停用")
  private String status;

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
