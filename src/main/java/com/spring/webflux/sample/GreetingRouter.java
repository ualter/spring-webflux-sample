package com.spring.webflux.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        //@formatter:off
        return RouterFunctions
                   .route(RequestPredicates.GET("/hello")  .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),              greetingHandler::hello)
                .andRoute(RequestPredicates.GET("/weather").and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)), greetingHandler::weather)   
                .andRoute(RequestPredicates.GET("/seconds").and(RequestPredicates.accept(MediaType.TEXT_EVENT_STREAM)),       greetingHandler::seconds);
        //@formatter:on
    }

}
