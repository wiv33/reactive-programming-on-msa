package org.psawesome.chrome;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.openqa.selenium.chrome.ChromeDriverService.*;

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
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndTests {

  static ChromeDriverService service;
  static ChromeDriver driver;

  @LocalServerPort
  int port;

  @BeforeAll
  static void beforeAll() throws IOException {
    System.setProperty("webdriver.chrome.driver", "ext/chromedriver");
    service = ChromeDriverService.createDefaultService();
    Objects.requireNonNull(service);
    driver = new ChromeDriver(service);

    final Path testResults = Paths.get("build", "test-results");
    if (!Files.notExists(testResults)) {
      Files.createDirectories(testResults);
    }
  }

  @AfterAll
  static void afterAll() {
    service.stop();
  }

  @Test
  void testContextInit() throws IOException {
    driver.get("http://localhost:" + port);
    takeScreenshot("homePageShouldWork-1");

  }


  private void takeScreenshot(String name) throws IOException {
    FileCopyUtils.copy(
            driver.getScreenshotAs(OutputType.FILE),
            new File("build/test-results/TEST-" + name + ".png"));
  }
}
