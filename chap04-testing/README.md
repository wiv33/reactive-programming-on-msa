Testing - chapter 04
=

chrome, chrome-driver version => ***85.0.4183.83***
- 

using selenium with chrome
-
[Test code](./src/test/java/org/psawesome/chrome/EndToEndTests.java)
[build.gradle.kts](../build.gradle.kts)
### 

- ChromeDriverService.createDefaultService
- new ChromeDriver()

project build.gradle.kts
-

```kotlin
    project(":chap04-testing") {
        dependencies {
            extra["bytebuddyVersion"] = "1.8.22"
            extra["mockitoVersion"] = "2.22.0"
    
            testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
            implementation("io.projectreactor:reactor-test")
            implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    
            testImplementation("org.mockito:mockito-core:${extra["mockitoVersion"]}")
    
            testImplementation("org.seleniumhq.selenium:htmlunit-driver")
            testImplementation("org.seleniumhq.selenium:selenium-java")
    
            testImplementation("com.google.guava:guava:23.0")
        }
    }
```