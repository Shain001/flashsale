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
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Filter illegal userId and saleId to minimize the workload for backend services
     *
     * Filter saleIds that are impossible to exist by using bloom filter
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        // Get Request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // Add log
        log.info("send {} reqeust to {}" + request.getMethod(), ctx.get("proxy"));

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
