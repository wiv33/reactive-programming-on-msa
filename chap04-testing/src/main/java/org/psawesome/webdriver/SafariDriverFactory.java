package org.psawesome.webdriver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

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
public class SafariDriverFactory implements ObjectFactory<SafariDriver> {
  private final WebDriverConfigurationProperties properties;

  @Override
  public SafariDriver getObject() throws BeansException {
    if (!properties.getSafari().isEnabled()) return null;
    return new SafariDriver();
  }

}
