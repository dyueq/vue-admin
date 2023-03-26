package com.sb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sb.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author: dyq
 * Time: 2023/3/16
 * description: 描述
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<Long> getNavMenuIds(Long userId);

    List<User> userListByMenuId(Long menuId);
}
