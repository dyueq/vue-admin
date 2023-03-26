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
public class User {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String avatar;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    private String city;
    private Timestamp created;
    private Timestamp updated;
    private Timestamp lastLogin;
    private Integer statu;
    @TableField(exist = false)
    private List<Role> roleList = new ArrayList<>();
}
