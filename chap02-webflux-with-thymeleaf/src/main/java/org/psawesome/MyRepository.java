package org.psawesome;

import reactor.core.publisher.Flux;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 8. 15. Saturday
 */
public interface MyRepository {
  Flux<Image> findAll();
}
