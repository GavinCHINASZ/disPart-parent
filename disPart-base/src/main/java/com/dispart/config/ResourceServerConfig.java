//package com.dispart.config;
//
//import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
///**
// * @author: wujie
// * @create: 2019-10-08 10:04
// **/
//@Configuration
//@EnableResourceServer
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Slf4j
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    private static final String RESOURCE_ID = "resource-server";
//
////    @Autowired
////    private UserInfoTokenServices userInfoTokenServices;
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                // 配置不需要安全拦截url
//                .antMatchers("/DISP20210063","/DISP20210061","/DISP20210062","/DISP20210064","/DISP20210065","/DISP20210053").permitAll()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .anyRequest().authenticated();
//
//    }
//
//    /**
//     * 这个是跟服务绑定的，注意要跟client配置一致，如果客户端没有，则不能访问
//     * @param resources
//     * @throws Exception
//     */
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources/*.resourceId(RESOURCE_ID)*/.stateless(true);
////        userInfoTokenServices.setPrincipalExtractor(principalExtractor());
//        // 配置了user-info-uri默认使用的就是userInfoTokenServices，这个这么配置只是为了设置principalExtractor
//       // resources.tokenServices(userInfoTokenServices);
//    }
//
//    /**
//     * 自定义Principal提取器，返回的Principal是一个map
//     *
//     * @return
//     */
////    @Bean
////    public PrincipalExtractor principalExtractor() {
////        return new CustomizePrincipalExtractor();
////    }
//
//}
