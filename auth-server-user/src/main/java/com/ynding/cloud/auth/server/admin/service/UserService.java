package com.ynding.cloud.auth.server.admin.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ynding.cloud.auth.server.admin.data.UserRepository;
import com.ynding.cloud.auth.server.admin.entity.QUser;
import com.ynding.cloud.auth.server.admin.entity.User;
import com.ynding.cloud.common.model.bo.Query;
import com.ynding.cloud.common.model.bo.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private JPAQueryFactory jpaQueryFactory;

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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        User user = User.builder()
                .username(username)
                .password(encode)
                .build();
        return ResponseBean.ok(userRepository.save(user));
    }


    /**
     * 查询用户列表
     * @param query
     * @return
     */
    public List<User> findList(Query query) {
        QUser user = QUser.user;
        return (List<User>) userRepository.findAll(predicate(query,user));
    }

    /**
     * 分页查询
     * @param query
     * @return
     */
    public Page<User> pageList(Query query) {
        QUser user = QUser.user;
        Pageable pageable =  PageRequest.of(query.getOffset(), query.getLimit(), Sort.Direction.DESC, "id");
        return userRepository.findAll(predicate(query,user),pageable);
    }

    /**
     * 查询用户列表(取前几个)
     * @param query
     * @return
     */
    public List<User> findTop(Query query){
        QUser user = QUser.user;
        List<User> users = jpaQueryFactory
                .selectFrom(user)
                .where(predicate(query,user))
                .orderBy(user.id.desc())
                .limit(2)
                .fetch();

        return users;
    }

    /**
     * 断言
     * @param query
     * @return
     */
    private Predicate predicate(Query query, QUser user){

        //初始化组装条件(类似where 1=1)
        Predicate predicate = user.isNotNull().or(user.isNull());
        if(query.get("username") != null) {
            predicate = ExpressionUtils.and(predicate,user.username.eq((String) query.get("username")));
        }
        if(query.get("phone") != null) {
            predicate = ExpressionUtils.and(predicate,user.username.eq((String) query.get("phone")));
        }
        if(query.get("name") != null) {
            predicate = ExpressionUtils.and(predicate,user.name.like((String) query.get("name")));
        }
        if(query.get("address") != null) {
            predicate = ExpressionUtils.and(predicate,user.name.like((String) query.get("address")));
        }

        return predicate;
    }

    /**
     * 根据用户名和密码查找（假定只能找出一条）
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password) {
        QUser user = QUser.user;
        return jpaQueryFactory
                .selectFrom(user)
                .where(
                        user.username.eq(username),
                        user.password.eq(password)
                )
                .fetchOne();
    }

    /**
     *分页查询所有的实体,根据id字段排序
     *
     * @return
     */
    public QueryResults<User> findAllPage(Pageable pageable) {

        QUser user = QUser.user;
        return jpaQueryFactory
                .selectFrom(user)
                .orderBy(
                        user.id.asc()
                )
                .offset(pageable.getOffset())   //起始页
                .limit(pageable.getPageSize())  //每页大小
                .fetchResults();    //获取结果，该结果封装了实体集合、分页的信息，需要这些信息直接从该对象里面拿取即可
    }


}
