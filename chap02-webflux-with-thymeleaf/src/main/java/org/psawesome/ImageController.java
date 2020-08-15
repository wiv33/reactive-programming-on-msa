package org.psawesome;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
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
 * @since 20. 8. 15. Saturday
 */
@Log4j2
@RestController
public class ImageController {
  @GetMapping("/api/images")
  public Flux<Image> images() {
    return Flux.just(
            new Image("1", "ps1.jpg"),
            new Image("2", "ps2.jpg"),
            new Image("3", "ps3.png")
    );
  }

  @PostMapping("/api/images")
  public Mono<Void> create(@RequestBody Flux<Image> images) {
    return images
            .map(image -> {
              log.info("we will save {} to a Reactive database soon!", image);
              return image;
            })
            .then();
  }
}
