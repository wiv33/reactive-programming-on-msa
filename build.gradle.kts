plugins {
    java
    id("org.springframework.boot") version "2.0.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

allprojects {
    apply(plugin = "java")
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    group = "org.psawesome"
    version = "1.0.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_13

    extra["springCloudVersion"] = "Hoxton.SR5"

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
        maven {
            url = uri("http://repo.mycompany.com/repo")
            metadataSources {
                mavenPom()
                artifact()
                ignoreGradleMetadataRedirection()
            }
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${extra["springCloudVersion"]}")
        }
    }

    dependencies {
        extra["lombokVersion"] = "1.18.4"
        extra["jupiterVersion"] = "5.6.2"

        compileOnly("org.projectlombok:lombok:${extra["lombokVersion"]}")
        annotationProcessor("org.projectlombok:lombok:${extra["lombokVersion"]}")

        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
        // mockito, JSONassert, hamcrest, Assertj
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("org.junit.jupiter:junit-jupiter-api:${extra["jupiterVersion"]}")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:${extra["jupiterVersion"]}")
        testImplementation("org.junit.jupiter:junit-jupiter-params:${extra["jupiterVersion"]}")
        testImplementation("org.synchronoss.cloud:nio-multipart-parser")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":chap02-webflux-with-thymeleaf") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

//        Third part library - file upload
        implementation("org.synchronoss.cloud:nio-multipart-parser:1.1.0")
    }
}

project(":chap03-mongo") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.synchronoss.cloud:nio-multipart-parser:1.1.0")
    }
}

project(":chap04-testing") {
    dependencies {
        extra["bytebuddyVersion"] = "1.8.22"
        extra["mockitoVersion"] = "2.22.0"

        testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
        testImplementation("io.projectreactor:reactor-test")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")


        // tag::unsafe error[]

//        https://stackoverflow.com/questions/52533878/mockito-error-in-spring-boot-tests-after-migrating-to-jdk-11
        // tag::모키토 에러[]
        /*
        at org.springframework.boot.test.mock.mockito.MockDefinition.createMock(MockDefinition.java:157)
        ...
        Caused by: java.lang.UnsupportedOperationException: Cannot define class using reflection
        ...
        Caused by: java.lang.NoSuchMethodException: sun.misc.Unsafe.defineClass(java.lang.String,[B,int,int,java.lang.ClassLoader,java.security.ProtectionDomain)
         */
        testImplementation("org.mockito:mockito-core:${extra["mockitoVersion"]}")
        // end::Underlying exception : java.lang.UnsupportedOperationException: Cannot define class using reflection[]
//        testImplementation("net.bytebuddy:byte-buddy:${extra["bytebuddyVersion"]}")
//        testImplementation("net.bytebuddy:byte-buddy-agent:${extra["bytebuddyVersion"]}")
        // end::unsafe error[]


        implementation("org.seleniumhq.selenium:htmlunit-driver")
        implementation("org.seleniumhq.selenium:selenium-java")
//        testImplementation("org.seleniumhq.selenium:selenium-chrome-driver")

        // tag::active @ConfigurationProperties[]
//        https://docs.spring.io/spring-boot/docs/2.0.2.RELEASE/reference/html/configuration-metadata.html#configuration-metadata-annotation-processor
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        // end::active @ConfigurationProperties[]

        // tag::NoSuchMethodError java.util.stream.Collector[]
        /*
        <groupId>net.oneandone.reflections8</groupId>
        <artifactId>reflections8</artifactId>
        <version>0.11.6</version>
        */
//        testImplementation("net.oneandone.reflections8:reflections8:0.11.6")
        testImplementation("com.google.guava:guava:23.0")
        // end::NoSuchMethodError java.util.stream.Collector[]
    }
}