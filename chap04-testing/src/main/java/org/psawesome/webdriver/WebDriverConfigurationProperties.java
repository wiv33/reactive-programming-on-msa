package org.psawesome.webdriver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@Data
@ConfigurationProperties("org.psawesome.webdriver")
public class WebDriverConfigurationProperties {
  private Firefox firefox = new Firefox();
  private Safari safari = new Safari();
  private Chrome chrome = new Chrome();

  @Data
  static class Firefox {
    private boolean enabled = true;
  }

  @Data
  static class Safari {
    private boolean enabled = true;
  }

  @Data
  static class Chrome {
    private boolean enabled = true;
  }

}
