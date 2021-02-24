package com.ynding.cloud.auth.authentication.config.security;


import com.ynding.cloud.auth.authentication.data.MenuRepository;
import com.ynding.cloud.auth.authentication.entity.Menu;
import com.ynding.cloud.auth.authentication.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求url 对应 允许访问的角色
 * @author ynding
 */
@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuRepository menuRepository;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation)o).getRequestUrl();
        List<Menu> allMenu = menuRepository.findAll();
        for(Menu menu : allMenu){
            if(antPathMatcher.match(menu.getUrl(),requestUrl) && menu.getRoles().size() > 0){

                List<Role> roles = menu.getRoles();
                List<String> names = roles.stream().map(p -> p.getName()).collect(Collectors.toList());
                return SecurityConfig.createList(names.toArray(new String[names.size()]));
            }
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
