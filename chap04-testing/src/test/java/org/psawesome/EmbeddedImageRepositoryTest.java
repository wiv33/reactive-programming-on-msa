package org.psawesome;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

@ExtendWith(SpringExtension.class)
@DataMongoTest
class EmbeddedImageRepositoryTest {

  @Autowired
  ImageRepository imageRepository;

  @Autowired
  ReactiveFluentMongoOperations operations;

  @BeforeEach
  void setUp() {
    operations.insert(Image.class)
            .all(Stream.generate(() -> new Image("mybody.jpg"))
                    .limit(30)
                    .collect(Collectors.toList()))
            .subscribe(System.out::println);
  }

  @Test
  void testReactor() {
    StepVerifier.create(operations.query(Image.class)
            .all().log())
            .expectNextCount(30)
            .expectComplete()
            .verify();
  }

  @Test
  void testFindAllShouldWork() {
    final Flux<Image> all = imageRepository.findAll().log();
    StepVerifier.create(all)
            .expectNextCount(30)
            .verifyComplete();
  }
}
