package com.ynding.cloud.auth.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.sql.DataSource;

/**
 * 认证服务器配置类
 * EnableAuthorizationServer当前应用是一个认证服务器
 * 1，要知道有哪些客户端应用 来申请令牌
 * 2，要知道有哪些合法的用户
 * 3，要知道发出去的令牌，能够访问哪些资源服务器
 *
 * @author ynding
 * @version 2020/9/9
 **/
@Configuration
@EnableAuthorizationServer
@EnableRedisHttpSession
public class Oauth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DataSource dataSource;

    /**
     * redis 连接工厂，session 共享用
     * @return
     */
    @Bean
    public LettuceConnectionFactory connectionFactory(){
        return new LettuceConnectionFactory();
    }

    /**
     * tokenStore是进行存取token的接口，默认内存的实现还有redis，jdbc，jwt的实现
     */
    @Bean
    public TokenStore tokenStore(){
        //这里配置用jdbc进行存取token
//        return new JdbcTokenStore(dataSource);
        //jwt存取token
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * 对jwt进行签名的key，jwt是明文，签名防篡改。
     * 接收token的人需要用同样的key验签名，需要把这个key通过服务暴漏出去，使用服务的人才能拿到key
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("ynding");
        return converter;
    }

    /**
     * 1 配置客户端应用的信息，让认证服务器知道有哪些客户端应用来申请令牌。
     * ClientDetailsServiceConfigurer：客户端的详情服务的配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 从数据库中取
        clients.jdbc(dataSource);
        //配置在内存里，后面修改为数据库里
        /*clients.inMemory()
                //~============== 注册【客户端应用】,使客户端应用能够访问认证服务器 ===========
                .withClient("orderApp")
                .secret(passwordEncoder.encode("123456"))
                //orderApp有哪些权限
                .scopes("read", "write")
                //token的有效期
                .accessTokenValiditySeconds(3600)
                //资源服务器的id。发给orderApp的token，能访问哪些资源服务器，可以多个
                .resourceIds("order-server")
                //授权方式，再给orderApp做授权的时候可以用哪种授权方式授权
                .authorizedGrantTypes("password")
                //~=============客户端应用配置结束 =====================
                .and()
                //~============== 注册【资源服务器-订单服务】(因为订单服务需要来认证服务器验令牌),使订单服务也能够访问认证服务器 ===========
                .withClient("orderServer")
                .secret(passwordEncoder.encode("123456"))
                .scopes("read", "write")
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-server")
                .authorizedGrantTypes("password");*/
    }

    /**
     * 2 配置用户信息
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //传给他一个authenticationManager用来校验传过来的用户信息是不是合法的
        endpoints
                //告诉服务器要用自定义的tokenStore里去存取token
                .tokenStore(tokenStore())
                //加入jwt需要用到
                .tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager);
    }


    /**
     * 3，配置资源服务器过来验token 的规则
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                /**
                 * 过来验令牌有效性的请求，不是谁都能验的，必须要是经过身份认证的。
                 * 所谓身份认证就是，必须携带clientId，clientSecret，否则随便一请求过来验token是不验的
                 */
                .checkTokenAccess("isAuthenticated()")
                /**
                 * 暴漏获取jwt签名key的服务，只有经过身份认证的请求才能调（同上）
                 * 上边 tokenStore里的signKey
                 */
                .tokenKeyAccess("isAuthenticated()")
        ;
    }

}
