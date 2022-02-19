package icu.sunnyc.mapper;

import icu.sunnyc.entity.User;

/**
 * @author ：hc
 * @date ：Created in 2022/2/19 21:03
 * @modified ：
 */
public interface UserMapper {

    /**
     * 根据用户id获取User对象
     * @param id userId
     * @return User
     */
    User getUserById(String id);
}
