package org.psawesome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

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
@SpringBootApplication
public class ImagesApplication {
  public static void main(String[] args) {
//    System.setProperty("-Dspring.profiles.active", "big");
    SpringApplication.run(ImagesApplication.class, args);
  }
/*
  using tomcat
  @Bean
  public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new HiddenHttpMethodFilter();
  }*/
}
