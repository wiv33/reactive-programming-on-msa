package org.psawesome;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

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
@RequiredArgsConstructor
public class HomeController {
  private static final String BASE_PATH = "/images";
  private final String FILENAME = "{filename:.+}";

  private final ImageService imageService;

  @GetMapping
  public Mono<String> home(Model model) {
    model.addAttribute("images", imageService.findAllImages());
    return Mono.just("index");
  }

  @PostMapping(BASE_PATH)
  public Mono<String> createFile(@RequestPart(name = "file") Flux<FilePart> files) {
    return imageService.createImage(files)
            .then(Mono.just("redirect:/"));
  }

  @GetMapping(value = BASE_PATH + "/" + FILENAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  public Mono<ResponseEntity<?>> oneRawImage(@PathVariable String filename) {
    return imageService.findOneImage(filename)
            .map(resource -> {
              try {
                return ResponseEntity.ok()
                        .contentLength(resource.contentLength())
                        .body(new InputStreamResource(resource.getInputStream()));
              } catch (IOException e) {
                return ResponseEntity.badRequest()
                        .body(String.format("couldn't find %s => %s", filename, e.getMessage()));
              }
            });
  }

  @DeleteMapping(BASE_PATH + "/" + FILENAME)
  public Mono<String> delete(@PathVariable String filename) {
    return imageService.deleteImage(filename)
            .then(Mono.just("redirect:/"));
  }
}
