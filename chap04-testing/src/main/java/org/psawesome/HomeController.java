package org.psawesome;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
 * @since 20. 8. 21. Friday
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

  private static final String BASE_PATH = "/images";
  private static final String FILENAME = "{filename:.+}";

  private final ImageService imageService;


  @GetMapping
  public Mono<String> homePage(Model model) {
    model.addAttribute("images", imageService.findAllImages());
    // tag::주석해제 시 noInteractive test fail[]
//    model.addAttribute("images", imageService.findAllImages());
    // end::주석해제 시 noInteractive test fail[]
    return Mono.just("index");
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
  public Mono<String> deleteImage(@PathVariable String filename) {
    return imageService.deleteImage(filename)
            .then(Mono.just("redirect:/"));
  }
}
