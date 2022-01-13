package com.java.components;

import com.java.entity.User;
import com.java.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        String account = userToken.getUsername();
        List<User> user = service.getPasswdByAccount(account);

        if (user.size() == 0){
            // if user not exist, Shiro will automatically returns UserUnknownException
            return null;
        }

        User u = user.get(0);

        return new SimpleAuthenticationInfo(account,u.getPasswd(), "");
    }
}
