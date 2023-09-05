package com.example.mongowebflux.routers;

import com.example.mongowebflux.handler.PostHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class PostRouter {

    private final PostHandler postHandler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
            .GET("/posts", accept(MediaType.TEXT_PLAIN),
                postHandler::listPosts)
            .GET("/posts/{id}", accept(MediaType.TEXT_PLAIN),
                postHandler::getById)
            .POST("/posts", accept(MediaType.TEXT_PLAIN),
                postHandler::savePost)
            .build();
    }
}
