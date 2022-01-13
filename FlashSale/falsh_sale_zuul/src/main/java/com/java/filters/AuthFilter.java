package com.java.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        // Get Request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // allow all request for logging in
        String url = request.getRequestURI();

        if (url.equals("//login/login")){
            return null;
        }


        // for all other requests, checking the authentication
        Cookie[] cookies = request.getCookies();

        // check cookie account
        // if cookie:account exist means user has done authentication
        // otherwise intercept the request
        if (cookies != null){
            for (Cookie c:cookies ){
                if (c.getName().equals("account")){
                    return null;
                }
            }
        }

        // if arrive here, means unauthenticated, do the interception
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        ctx.setResponseBody("unauthenticated");

        return null;
    }
}
