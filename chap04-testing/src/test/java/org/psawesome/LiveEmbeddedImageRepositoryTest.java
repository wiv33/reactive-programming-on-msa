package org.psawesome;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicLong;
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
 * @since 20. 8. 21. Friday
 */
// TODO DRY (Do not Repeat Yourself)
@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class LiveEmbeddedImageRepositoryTest {

  @Autowired
  ImageRepository repository;

  @Autowired
  ReactiveFluentMongoOperations operations;

  @BeforeEach
  void setUp() {
    final AtomicLong atomicLong = new AtomicLong(0);
    operations.insert(Image.class)
            .all(Stream.generate(() -> new Image(String.format("my_body_%d.jpg", atomicLong.getAndIncrement()))).limit(33).collect(Collectors.toList()))
            .publishOn(Schedulers.newElastic("psawesome"))
            .log("--insertAll-newElastic")
            .subscribe();
  }

  @AfterEach
  void tearDown() {
    repository.deleteAll()
            .log("--deleteAll")
            .subscribe();
  }

  @Test
  void testSaveCount() {
    // required container up
    // docker-compose -f container/compose/docker-compose.yml up
    StepVerifier.create(operations.query(Image.class).all())
            .expectNextCount(33)
            .verifyComplete();
  }

}
