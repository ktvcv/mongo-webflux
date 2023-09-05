package com.example.mongowebflux.handler;

import com.example.mongowebflux.model.Post;
import com.example.mongowebflux.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PostHandler {

    private final PostRepository postRepository;

    public Mono<ServerResponse> savePost(final ServerRequest serverRequest) {
       return serverRequest.bodyToMono(Post.class)
            .flatMap(postRepository::save)
            .flatMap(p -> ServerResponse.created(URI.create("/posts/" + p.getId())).build());
    }

    public Mono<ServerResponse> listPosts(final ServerRequest serverRequest) {

        Flux<Post> all = postRepository.findAll();
        return ok().contentType(APPLICATION_JSON).bodyValue(all);
    }

    public Mono<ServerResponse> getById(final ServerRequest serverRequest) {
        return postRepository.findById(serverRequest.pathVariable("id"))
            .flatMap(post -> ServerResponse.ok().body(Mono.just(post), Post.class))
            .switchIfEmpty(ServerResponse.notFound().build());
    }
}
