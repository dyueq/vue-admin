package com.sb.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
*author: dyq
*Time: 2023/3/16  
*description: 描述
*/
@Data
public class Role {
    private Long id;
    @NotBlank(message = "角色名称不能为空")
    private String name;
    @NotBlank(message = "角色名称不能为空")
    private String code;
    private String remark;
    private Timestamp created;
    private Timestamp updated;
    private Integer statu;
    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();
}
