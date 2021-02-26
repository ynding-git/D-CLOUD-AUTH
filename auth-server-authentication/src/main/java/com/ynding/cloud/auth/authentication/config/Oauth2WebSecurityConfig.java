package com.ynding.cloud.auth.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ynding.cloud.auth.authentication.config.security.AuthenticationAccessDeniedHandler;
import com.ynding.cloud.auth.authentication.config.security.CustomMetadataSource;
import com.ynding.cloud.auth.authentication.config.security.LindTokenAuthenticationFilter;
import com.ynding.cloud.auth.authentication.config.security.UrlAccessDecisionManager;
import com.ynding.cloud.auth.authentication.entity.User;
import com.ynding.cloud.common.model.bo.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 使安全配置生效
 *
 * @author ynding
 * @version 2020/9/9
 **/
@Slf4j
@Configuration
@EnableWebSecurity
public class Oauth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomMetadataSource metadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler deniedHandler;
    /**
     * token过滤器.
     */
    @Autowired
    LindTokenAuthenticationFilter lindTokenAuthenticationFilter;


    /**
     * AuthenticationManagerBuilder 是用来构建  AuthenticationManager（处理登录操作）的
     * 需要两个东西：userDetailsService  、passwordEncoder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //获取用户信息
        auth.userDetailsService(userDetailsService)
                //比对密码
                .passwordEncoder(passwordEncoder);
    }

    /**
     * 把AuthenticationManager暴露为bean
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * HttpSecurity是WebSecurity的一部分
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //不做拦截的路径
        web.ignoring().antMatchers("/", "/index.html", "/ws/**", "/resources/**",
                "static/**", "/h2-console/**", "/login_p", "/login", "/favicon.ico")
                // token 请求不做拦截
                .antMatchers("/token/**")
                //加上过滤，使不经过安全认证就可访问swagger的相关资源
                .antMatchers("/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**")
                .antMatchers("/ws.html")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();//禁用security的csrf

        // 将H2数据库控制台设置为可以访问
        http.authorizeRequests().antMatchers("/h2-console/**", "/lind-auth/**",
                "/login", "/**/register")
                .permitAll();
        // 临时允许同一来源的H2数据库控制台的请求
        http.headers().frameOptions().sameOrigin();

        //带有正确token的可以过滤掉下面的认证
        http.addFilterBefore(lindTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //任何请求会跳到登录界面
        http
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        // 访问url 应有的权限
                        o.setSecurityMetadataSource(metadataSource);
                        // 权限判定
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login_p")//登录页
//                .loginProcessingUrl("/login")//登录提交的处理Url
//                .successHandler(loginSuccessHandler())
//                .failureHandler(authenticationFailureHandler())
//                .permitAll()//允许所有用户都有权限访问登录页面
//                .and().logout().permitAll().logoutSuccessHandler(logoutSuccessHandler())
//                .and().exceptionHandling().accessDeniedHandler(deniedHandler)
        ;

        http.headers().cacheControl();// 禁用缓存

    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {//登录失败处理
        return (req, resp, e) -> {
            resp.setContentType("application/json;charset=utf-8");
            ResponseBean respBean = null;
            if (e instanceof BadCredentialsException ||
                    e instanceof UsernameNotFoundException) {
                respBean = ResponseBean.fail("账户名或者密码输入错误!");
            } else if (e instanceof LockedException) {
                respBean = ResponseBean.fail("账户被锁定，请联系管理员!");
            } else if (e instanceof CredentialsExpiredException) {
                respBean = ResponseBean.fail("密码过期，请联系管理员!");
            } else if (e instanceof AccountExpiredException) {
                respBean = ResponseBean.fail("账户过期，请联系管理员!");
            } else if (e instanceof DisabledException) {
                respBean = ResponseBean.fail("账户被禁用，请联系管理员!");
            } else {
                respBean = ResponseBean.fail("登录失败!");
            }
            resp.setStatus(401);
            ObjectMapper om = new ObjectMapper();
            PrintWriter out = resp.getWriter();
            out.write(om.writeValueAsString(respBean));
            out.flush();
            out.close();
        };
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                User userDetails = (User) authentication.getPrincipal();
                logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                    User user = (User) authentication.getPrincipal();
                    log.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
                } catch (Exception e) {
                    log.info("LOGOUT EXCEPTION , e : " + e.getMessage());
                }
                httpServletResponse.sendRedirect("/login");
            }
        };
    }


}
