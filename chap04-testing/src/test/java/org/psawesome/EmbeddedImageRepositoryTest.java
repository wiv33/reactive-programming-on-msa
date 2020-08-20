package org.psawesome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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
    AtomicInteger integer = new AtomicInteger(0);
    operations.insert(Image.class)
            .all(Stream.generate(() -> new Image(String.format("mybody_%d.jpg", integer.getAndIncrement())))
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

  @Test
  void testFindByNameShouldWork() {
    Hooks.onOperatorDebug();

    final Flux<Image> name = operations.query(Image.class)
            .matching(Query.query(where("name").regex("7")))
            .all()
            .delaySubscription(Duration.ofSeconds(10_000))
            .publishOn(Schedulers.single())
            ;

    StepVerifier.withVirtualTime(name::log, 3)
            .expectSubscription()
            .expectNoEvent(Duration.ofSeconds(10_000))
            .thenAwait(Duration.ofSeconds(100))
            .expectNextCount(3)
            .expectComplete()
            .verify()
    ;

/*
    StepVerifier.create(name)
            .expectNextCount(3)
            .verifyComplete();
*/
  }
}
