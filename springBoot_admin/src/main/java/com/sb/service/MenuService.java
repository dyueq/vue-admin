package com.sb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sb.bean.Menu;
import com.sb.bean.dto.MenuDto;

import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
public interface MenuService extends IService<Menu> {
    List<MenuDto> getCurrentUserMenu();

    List<Menu> menuTree();
}
