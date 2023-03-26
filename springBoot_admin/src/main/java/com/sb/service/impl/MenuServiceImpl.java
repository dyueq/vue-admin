package com.sb.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sb.bean.Menu;
import com.sb.bean.User;
import com.sb.bean.dto.MenuDto;
import com.sb.mapper.MenuMapper;
import com.sb.mapper.UserMapper;
import com.sb.service.MenuService;
import com.sb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Lazy
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<MenuDto> getCurrentUserMenu() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUsername(username);
        List<Long> menuIds = userMapper.getNavMenuIds(user.getId());
        List<Menu> menuList = this.listByIds(menuIds);
        //转树状结构
        List<Menu> menuTree = buildMenuTree(menuList);
        //实体转dto
        return convert(menuTree);
    }

    @Override
    public List<Menu> menuTree() {
        //获取所有菜单信息
        List<Menu> menuList = this.list(new QueryWrapper<Menu>().orderByAsc("order_num"));
        //转成树状结构
        return buildMenuTree(menuList);
    }

    private List<MenuDto> convert(List<Menu> menuTree){
        List<MenuDto> menuDtos = new ArrayList<>();
        menuTree.forEach(menu -> {
            MenuDto menuDto = new MenuDto();
            menuDto.setId(menu.getId());
            menuDto.setName(menu.getPram());
            menuDto.setLabel(menu.getName());
            menuDto.setPath(menu.getPath());
            menuDto.setIcon(menu.getIcon());
            menuDto.setComponent(menu.getComponent());
            if (menu.getChildren().size() > 0){
                //子节点调用当前方法进行转换
                menuDto.setChildren(convert(menu.getChildren()));
            }
            menuDtos.add(menuDto);
        });
        return menuDtos;
    }
    private List<Menu> buildMenuTree(List<Menu> menuList) {
        ArrayList<Menu> finalMenuList = new ArrayList<>();
        //先找到各自的孩子
        for (Menu menu : menuList) {
            for (Menu m : menuList) {
                if (menu.getId() == m.getParentId()){
                    menu.getChildren().add(m);
                }
            }
            if (menu.getParentId() == 0L){
                finalMenuList.add(menu);
            }
        }
        System.out.println(JSONUtil.toJsonStr(finalMenuList));
        //提取出父节点
        return finalMenuList;
    }
}
