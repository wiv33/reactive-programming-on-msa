package org.psawesome;

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
 * @since 20. 8. 16. Sunday
 */
@Component
public class InitComponent {

  @Bean
  CommandLineRunner setUp(ReactiveFluentMongoOperations operations) {
    return args -> {
      operations.insert(Image.class)
              .all(List.of(
                      new Image("1", "ps.png"),
                      new Image("2", "awesome.jpg"),
                      new Image("3", "body.jpg")
              ));

      operations.query(Image.class)
              .all()
              .subscribe(System.out::println);
    };
  }
}
