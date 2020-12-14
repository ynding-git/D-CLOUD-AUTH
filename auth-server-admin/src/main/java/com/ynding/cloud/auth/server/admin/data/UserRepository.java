package com.ynding.cloud.auth.server.admin.data;


import com.ynding.cloud.auth.server.admin.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author ynding
 */
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    /**
     * 根据用户名查找用户
     */
    User findUserByUsername(String username);
}
