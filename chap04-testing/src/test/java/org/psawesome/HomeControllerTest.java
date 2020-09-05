package org.psawesome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
  WebTestClient testClient;

  // tag::Error creating bean with name 'org.psawesome.HomeControllerTest': Unsatisfied dependency expressed through field 'operations'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.data.mongodb.core.ReactiveFluentMongoOperations' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
//  @Autowired
//  ReactiveFluentMongoOperations operations;
  // end::Error creating bean with name 'org.psawesome.HomeControllerTest': Unsatisfied dependency expressed through field 'operations'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.data.mongodb.core.ReactiveFluentMongoOperations' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

  @MockBean
  ImageService imageService;

  @Autowired
  HomeController controller;

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

    final EntityExchangeResult<String> result = testClient.get().uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult();

//    imageService에서 findAllImages가 호출됐다는 것을 증명
    verify(imageService).findAllImages();
//    이후 다른 호출이 없다는 것을 증명
//    HomeController 주석 해제 시 fail 확인 가능
    verifyNoMoreInteractions(imageService);

    assertTrue(Objects.requireNonNull(result.getResponseBody())
            .contains("<title>Image Home page</title>"));
  }


  WebClient client = WebClient.builder().baseUrl("http://localhost:8080")
          .build();

  @Test
  @DisplayName("반복 학습 코드")
  void testPractice() {
    var alpha = new Image("1", "psawesome_1.jpg");
    var bravo = new Image("2", "psawesome_2.jpg");
    given(imageService.findAllImages()).willReturn(Flux.just(alpha, bravo));

    final FluxExchangeResult<String> result = testClient.get()
            .uri("/").exchange()
            .expectStatus().isOk()
            .returnResult(String.class);

    verify(imageService).findAllImages();
    verifyNoMoreInteractions(imageService);

    result.getResponseBody()
            .log()
            .buffer()
            .flatMap(s -> Flux.fromStream(s.stream()))
            .reduce((acc, s) -> acc + s.trim())
            .log()
            .subscribe(body -> assertAll(
                    () -> assertEquals(HttpMethod.GET, result.getMethod()),
                    () -> assertTrue(body.contains("<title>Image Home page</title>")),
                    () -> assertTrue(body.contains("<td>psawesome_1.jpg</td>")),
                    () -> assertTrue(body.contains("action=\"/images/psawesome_2.jpg\"><input type=\"hidden\" name=\"_method\" value=\"delete\"/>")),
                    () -> assertTrue(body.contains("<img src=\"/images/psawesome_2.jpg/raw\"")),
                    () -> assertTrue(body.contains("<body>"))
            ));
  }

  @ParameterizedTest(name = "[{index}] {argumentsWithNames}")
  @DisplayName("test practice code")
  @ValueSource(strings = {"alpha", "bravo", "transforms", "clip"})
  void testPracticeCode(String name) {
    final Image image = new Image(Instant.now().toString(), String.format("%s.jpg", name));
    given(imageService.findAllImages()).willReturn(Flux.just(image));

//    when(imageService.findAllImages()).thenReturn(Flux.just(image));

    final FluxExchangeResult<String> result = testClient.get()
            .uri("/")
            .exchange()
            .expectStatus().isOk()
            .returnResult(String.class);

    verify(imageService).findAllImages();
    verifyNoMoreInteractions(imageService);

    result.getResponseBody()
            .buffer()
            .reduceWith(StringBuilder::new, (acc, str) -> acc.append(str.stream().reduce((a, b) -> a + b.trim())))
            .log()
            .map(String::valueOf)
            .subscribe(s -> assertTrue(s.contains(name)));
  }

  @Test
  @DisplayName("test should be fetching image")
  void testShouldBeFetchingImage() {
    when(imageService.findOneImage(any()))
            .thenReturn(Mono.just(new ByteArrayResource("data".getBytes())));

    testClient.get()
            .uri("/images/alpha.png/raw")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("data");

    verify(imageService).findOneImage("alpha.png");
    verifyNoMoreInteractions(imageService);
  }

  @ParameterizedTest(name = "[{index}] {argumentsWithNames}")
  @DisplayName("파일이 없을 때 요청에 대한 응답")
  @ValueSource(strings = {"alpha", "bravo", "clip"})
  void fetchingNullImageShouldFail(String filename) throws IOException {
    final Resource resource = mock(Resource.class);
    given(resource.getInputStream()).willThrow(new IOException("bad file"));
    given(imageService.findOneImage(any()))
            .willReturn(Mono.just(resource));

    testClient.get()
            .uri(String.format("/images/%s/raw", filename))
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(String.class)
            .isEqualTo(String.format("couldn't find %s => bad file", filename));

    verify(imageService).findOneImage(any());
    verifyNoMoreInteractions(imageService);
  }
}