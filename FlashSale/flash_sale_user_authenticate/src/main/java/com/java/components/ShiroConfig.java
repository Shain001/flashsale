package com.java.components;

import org.apache.catalina.realm.UserDatabaseRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configure for using Shiro
 *
 * Always 3 things to be configured:
 * (1) Realm -> data management
 * (2) SubjectManger -> Manage all subjects
 * (3) ShiroFilterFactoryBean -> Factory pattern for configuring filters
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager sm){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(sm);
        return shiroFilterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("realm") Realm realm){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(realm);
        return defaultSecurityManager;
    }

    @Bean
    public Realm realm(){
        return new ShiroRealm();
    }

}
