package com.ynding.cloud.auth.server.admin.service;

import com.ynding.cloud.auth.server.admin.data.MenuRepository;
import com.ynding.cloud.auth.server.admin.entity.Menu;
import com.ynding.cloud.common.model.bo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    public List<Menu> findList(Query query){
        return menuRepository.findAll();
    }

    public Page<Menu> pageList(Query query){

        Pageable pageable =  PageRequest.of(query.getOffset(), query.getLimit(), Sort.Direction.DESC, "id");
        return menuRepository.findAll(pageable);
    }

}
