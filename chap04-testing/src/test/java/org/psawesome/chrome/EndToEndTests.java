package org.psawesome.chrome;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.junit.jupiter.api.Assertions.*;

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
  @DisplayName("페이지 기능의 증명 - view, image data, delete function")
  void testContextInit() throws IOException {
    driver.get("http://localhost:" + port);
    takeScreenshot("homePageShouldWork-1");

    assertEquals("Image Home page", driver.getTitle());

    final String pageContent = driver.getPageSource();

    assertAll(
            () -> assertTrue(pageContent.contains("psawesome.jpg")),
            () -> assertTrue(pageContent.contains("alpha.jpg")),
            () -> assertTrue(pageContent.contains("bravo.jpg"))
    );

    final WebElement element = driver.findElement(By.cssSelector("[href*=\"alpha.jpg\"]"));

    final Actions actions = new Actions(driver);
    actions.moveToElement(element).click().perform();

    takeScreenshot("homePageShouldWork-2");

    driver.navigate().back();

    final WebElement deleteElement = driver.findElement(By.cssSelector("[action*=\"psawesome.jpg\"]"));
    actions.click(deleteElement).perform();

    assertFalse(driver.getPageSource().contains("psawesome.jpg"));
    takeScreenshot("homePageShouldWork-3");
  }


  private void takeScreenshot(String name) throws IOException {
    FileCopyUtils.copy(
            driver.getScreenshotAs(OutputType.FILE),
            new File("build/test-results/TEST-" + name + ".png"));
  }
}
