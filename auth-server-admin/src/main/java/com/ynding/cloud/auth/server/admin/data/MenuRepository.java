package com.ynding.cloud.auth.server.admin.data;

import com.ynding.cloud.auth.server.admin.entity.Menu;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@CacheConfig(cacheNames = "menus")
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

     /**
      * 查找所有
      */
     @Override
     @Cacheable
     List<Menu> findAll();
}
