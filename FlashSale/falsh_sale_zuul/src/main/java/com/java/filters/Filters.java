package com.java.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.redisson.api.RBloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class Filters extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(Filters.class);

    @Autowired
    private RBloomFilter<Integer> rBloomFilter;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Filter illegal userId and saleId to minimize the workload for backend services
     *
     * Filter saleIds that are impossible to exist by using bloom filter
     *
     *         TODO: note that here the check for parameters is only considering current exists APIs, which are
     *          sale api and payment api only, all of these two are using same two parameters only: userId and saleId
     *          Therefore, it is ok to intercept all illegal reqeust by checking these two here.
     *          However, if the project is updated for more modules with more APIs, the checking for parameters should be
     *          done according to specific APIs, since at that time, the parameters may varies.
     *          For example, if a "adding product api" is added for admins/retailers, then this API's will not use the parameters
     *          saleId and userId. As a result, if this part of code is not modified, then all the request for this new API
     *          will be intercepted.
     *          To solve that problem, every request should be checked respectively according to the url.
     *          e.g.
     *                 String url = request.getRequestURI();
     *                 String method = request.getMethod();
     *                 if (url.equals(xxx)){
     *                       filtering parameters..
     *                  }
     *
     *
     * @lastUpdate 2022/01/13
     * @author  Shenyi Zhang
     */
    @Override
    public Object run() throws ZuulException {

        // Get Request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // allow all request for logging in
        if (request.getRequestURI().equals("//login/login")){
            return null;
        }

        // Get Request Parameter
        String userId = request.getParameter("userId") + "";
        String saleId = request.getParameter("saleId") + "";

        // filter 1. check if userId/saleId is numeric and if they are positive number to filter out illegal reqeust
        if (!checkId(userId) || !checkId(saleId)) {
            log.warn("illegal request");
            // config response
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("illegal request");
            return null;
        }

        // filter 2. bloom filter for saleIds that not exist
        if (!rBloomFilter.contains(Integer.parseInt(saleId))){
            log.warn("not exit flash sale");
            // config response
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("not exit flash sale");
            return null;
        }


        return null;
    }

    public boolean checkId(String id){
        try{
            if (Integer.parseInt(id) <= 0){
                return false;
            }
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
