package org.psawesome;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 9. 5. Saturday
 */
@Component
@RequiredArgsConstructor
public class InitDatabase {

  private final ReactiveFluentMongoOperations operations;

  @Bean
  CommandLineRunner setUpData() {
    return args -> {
      operations.insert(Image.class)
              .all(List.of(
                      new Image("1", "psawesome.jpg"),
                      new Image("2", "alpha.jpg"),
                      new Image("3", "bravo.jpg")))
              .log()
              .subscribe();
    };
  }
}
