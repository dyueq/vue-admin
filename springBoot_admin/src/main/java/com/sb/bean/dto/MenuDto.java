package com.sb.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/21
 * description: 描述
 */
@Data
public class MenuDto implements Serializable {

    private Long id;
    private String name;
    private String label;
    private String icon;
    private String path;
    private String component;
    private List<MenuDto> children = new ArrayList<>();
}
