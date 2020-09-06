package org.psawesome.cutomDriver;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.psawesome.webdriver.FirefoxDriverFactory;
import org.psawesome.webdriver.WebDriverAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
public class WebDriverAutoConfigurationTests {
  private AnnotationConfigApplicationContext context;

  @After
  public void close() {
    if (nonNull(this.context)) {
      this.context.close();
    }
  }

  private void load(Class<?>[] configs, String... environment) {
    AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();
    configApplicationContext.register(WebDriverAutoConfiguration.class);
    if (configs.length > 0) configApplicationContext.register(configs);

//    EnvironmentTestUtils.addEnvironment(configApplicationContext, environment);
    TestPropertyValues.of(environment)
            .applyTo(configApplicationContext);
    configApplicationContext.refresh();
    this.context = configApplicationContext;
  }

  @Test
  void fallbackToNonGuiModeWhenAllBrowsersDisabled() {
    load(new Class[]{},
            "com.greglturnquist.webdriver.firefox.enabled:false",
            "com.greglturnquist.webdriver.safari.enabled:false",
            "com.greglturnquist.webdriver.chrome.enabled:false");

    WebDriver driver = context.getBean(WebDriver.class);
    assertFalse(ClassUtils.isAssignable(TakesScreenshot.class, driver.getClass()));
    assertTrue(ClassUtils.isAssignable(HtmlUnitDriver.class, driver.getClass()));
  }


  @Test
  void testWithMockedFirefox() {
    load(new Class[]{MockFirefoxConfiguration.class},
            "com.greglturnquist.webdriver.safari.enabled:false",
            "com.greglturnquist.webdriver.chrome.enabled:false");
    WebDriver driver = context.getBean(WebDriver.class);
    assertTrue(ClassUtils.isAssignable(TakesScreenshot.class, driver.getClass()));
    assertTrue(ClassUtils.isAssignable(FirefoxDriver.class, driver.getClass()));
  }

  @Configuration
  protected static class MockFirefoxConfiguration {
    @Bean
    FirefoxDriverFactory firefoxDriverFactory() {
      FirefoxDriverFactory factory =
              mock(FirefoxDriverFactory.class);
      given(factory.getObject())
              .willReturn(mock(FirefoxDriver.class));
      return factory;
    }
  }
}
