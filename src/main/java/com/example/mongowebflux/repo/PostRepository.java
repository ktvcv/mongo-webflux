package com.example.mongowebflux.repo;

import com.example.mongowebflux.model.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {

    @Override
    Flux<Post> findAll();
}
