package org.psawesome;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * @since 20. 8. 21. Friday
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

  private final ImageService imageService;

  @GetMapping
  public Mono<String> homePage(Model model) {
    model.addAttribute("images", imageService.findAllImages());
    return Mono.just("index");
  }
}
