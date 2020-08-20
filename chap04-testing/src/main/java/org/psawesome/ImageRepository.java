package org.psawesome;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 20. Thursday
 */
public interface ImageRepository extends ReactiveMongoRepository<Image, String> {
}
