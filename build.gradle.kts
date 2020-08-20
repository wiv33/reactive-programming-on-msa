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
        testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
        implementation("io.projectreactor:reactor-test")
    }
}