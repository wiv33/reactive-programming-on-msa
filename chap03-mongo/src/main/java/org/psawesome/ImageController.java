package org.psawesome;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
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
 * @since 20. 8. 17. Monday
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {
  private final String BASE_URL = "/api";

  private final ImageService imageService;

  @GetMapping(BASE_URL + "/images")
  public Flux<Image> images() {
    return imageService.findAllImages();
  }

  @PostMapping(BASE_URL + "/images")
  public Mono<Void> create(@RequestBody Flux<FilePart> images) {
    return imageService.createImage(images);
  }
}
