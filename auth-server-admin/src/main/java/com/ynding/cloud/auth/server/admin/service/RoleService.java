package com.ynding.cloud.auth.server.admin.service;

import com.ynding.cloud.auth.server.admin.data.RoleRepository;
import com.ynding.cloud.auth.server.admin.entity.Role;
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
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findList(Query query){
        return roleRepository.findAll();
    }

    public Page<Role> pageList(Query query){

        Pageable pageable =  PageRequest.of(query.getOffset(), query.getLimit(), Sort.Direction.DESC, "id");
        return roleRepository.findAll(pageable);
    }
}
