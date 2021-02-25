package com.ynding.cloud.auth.authentication.service;

import com.ynding.cloud.auth.authentication.data.UserRepository;
import com.ynding.cloud.auth.authentication.entity.User;
import com.ynding.cloud.common.model.bo.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ynding.cloud.common.model.bo.ResponseCode.USER_AlREAD_EXIST_EXCEPTION;

/**
 * @author ynding
 */
@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @Transactional(readOnly = false)
    public ResponseBean userReg(String username, String password) {
        //如果用户名存在，返回错误
        if (userRepository.findUserByUsername(username) != null) {
            return ResponseBean.fail(USER_AlREAD_EXIST_EXCEPTION);
        }
        String encode = passwordEncoder.encode(password);
        User user = User.builder()
                .username(username)
                .password(encode)
                .build();
        return ResponseBean.ok(userRepository.save(user));
    }

}
