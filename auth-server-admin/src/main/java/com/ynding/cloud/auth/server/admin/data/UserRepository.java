package com.ynding.cloud.auth.server.admin.data;


import com.ynding.cloud.auth.server.admin.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    User findUserByUsername(String username);
}
