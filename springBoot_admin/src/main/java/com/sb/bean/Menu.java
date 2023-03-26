package com.sb.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@Data
public class Menu {
    private Long id;
    @NotNull(message = "上级菜单不能为空")
    private Long parentId;
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    private String path;
    @NotBlank(message = "菜单权限不能为空")
    private String pram;
    private String component;
    @NotNull(message = "菜单类型不能为空")
    private Integer type;
    private String icon;
    private Integer orderNum;
    private Timestamp created;
    private Timestamp updated;
    private Integer statu;
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();

}
