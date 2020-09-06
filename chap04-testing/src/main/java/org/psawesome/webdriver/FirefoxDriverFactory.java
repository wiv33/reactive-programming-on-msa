package org.psawesome.webdriver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

import javax.validation.constraints.NotNull;

/**
 * @author ps [https://github.com/wiv33/reactive-programming-with-msa]
 * @role
 * @responsibility
 * @cooperate {
 * input:
 * output:
 * }
 * @see
 * @since 20. 9. 6. Sunday
 */
@RequiredArgsConstructor
public class FirefoxDriverFactory implements ObjectFactory<FirefoxDriver> {
  private final WebDriverConfigurationProperties properties;

  /**
   * @return FirefoxDriver
   */
  @Override
  public FirefoxDriver getObject() throws BeansException {
    if (!properties.getFirefox().isEnabled()) return null;
    return new FirefoxDriver();
  }
}
