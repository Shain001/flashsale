package com.java.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String tryLogin(String userAccount, String passwd, HttpServletRequest request, HttpServletResponse response){

        // get current subjects, i.e. user.
        Subject subject = SecurityUtils.getSubject();

        // Package the recieved account and psswd as a token
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, passwd);

        try {
            // This is the login method, which is actually calling the doAuthentication method defined in the Realm Class
            subject.login(token);

            // if login success, set cookie
            SimpleCookie simpleCookie = new SimpleCookie();
            simpleCookie.setName("account");
            simpleCookie.setValue(userAccount);
            simpleCookie.setMaxAge(24*60*60);
            simpleCookie.saveTo(request, response);

        } catch (UnknownAccountException e) {
            return "account not exist";
        } catch (IncorrectCredentialsException e){
            return "wrong passwd";
        }

        return "login sucess";
    }
}
