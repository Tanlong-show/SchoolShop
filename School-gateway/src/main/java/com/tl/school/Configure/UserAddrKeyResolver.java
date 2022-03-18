package com.tl.school.Configure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 根据userid限流
 **/
@Component
public class UserAddrKeyResolver implements KeyResolver {

    public UserAddrKeyResolver userAddrKeyResolver() {
        return new UserAddrKeyResolver();
    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
//        System.out.println(exchange.getRequest().getQueryParams().getFirst("userid"));
        if(exchange.getRequest().getQueryParams().getFirst("userid") == null){
            return Mono.just("1");
        }
        return Mono.just(exchange.getRequest().getQueryParams().getFirst("userid"));
    }

}







