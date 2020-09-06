package org.psawesome.webdriver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

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
@Configuration
@ConditionalOnClass(WebDriver.class)
@RequiredArgsConstructor
@EnableConfigurationProperties(WebDriverConfigurationProperties.class)
@Import({ChromeDriverFactory.class, FirefoxDriverFactory.class, SafariDriverFactory.class})
public class WebDriverAutoConfiguration {

  // tag::2[]
  private final WebDriverConfigurationProperties properties;
  // end::2[]

  // tag::3[]
  @Primary
  @Bean(destroyMethod = "quit")
  @ConditionalOnMissingBean(WebDriver.class)
  public WebDriver webDriver(
          FirefoxDriverFactory firefoxDriverFactory,
          SafariDriverFactory safariDriverFactory,
          ChromeDriverFactory chromeDriverFactory) {

    WebDriver driver = firefoxDriverFactory.getObject();

    if (driver == null) {
      driver = safariDriverFactory.getObject();
    }

    if (driver == null) {
      driver = chromeDriverFactory.getObject();
    }

    if (driver == null) {
      driver = new HtmlUnitDriver();
    }

    return driver;
  }
}
