package org.psawesome.webdriver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
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
public class ChromeDriverFactory implements ObjectFactory<ChromeDriver> {
  private final WebDriverConfigurationProperties properties;
  @Override
  public ChromeDriver getObject() throws BeansException {
    if (!properties.getChrome().isEnabled()) return null;
    return new ChromeDriver();
  }
}
