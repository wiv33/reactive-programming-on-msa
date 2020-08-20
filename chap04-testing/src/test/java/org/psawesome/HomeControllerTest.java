package org.psawesome;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
@ExtendWith({
        SpringExtension.class
})
@WebFluxTest(controllers = HomeController.class)
@Import({ThymeleafAutoConfiguration.class})
class HomeControllerTest {

  @Autowired
  WebTestClient webClient;

  @MockBean
  ImageService imageService;

  @BeforeEach
  void setUp() {
//    this.imageService =  Mockito.mock(ImageService.class);
  }

  @Test
  void testBaseRouteShouldListAllImages() {
    var alpha = new Image("1", "psawesome_1.jpg");
    var bravo = new Image("3", "psawesome_3.jpg");
    given(imageService.findAllImages())
            .willReturn(Flux.just(alpha, bravo));

    final EntityExchangeResult<String> result = webClient.get().uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult();

    verify(imageService).findAllImages();
    verifyNoMoreInteractions(imageService);

    Assertions.assertTrue(result.getResponseBody()
            .contains("<title>Image Home page</title>"));
  }
}