package org.psawesome;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

@DataMongoTest
class EmbeddedImageRepositoryTest {

  @Test
  public void testReactor() {
    StepVerifier.create(Mono.just(""));

  }
}
