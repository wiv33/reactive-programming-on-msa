package org.psawesome;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 16. Sunday
 */
public interface ImageRepository extends ReactiveMongoRepository<Image, String> {

  Mono<Image> findByName(String name);
}
