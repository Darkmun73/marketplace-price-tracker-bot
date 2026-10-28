plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.darkmun"
version = "0.0.1-SNAPSHOT"
description = "MarketplacePriceTrackerBot"

extra["jackson.version"] = "2.22.4"


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.projectlombok:lombok")
    implementation("org.telegram:telegrambots-springboot-longpolling-starter:10.3.0")
    implementation("org.telegram:telegrambots-client:10.3.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //.env file support
    developmentOnly(platform("me.paulschwarz:spring-dotenv-bom:5.1.0"))
    developmentOnly("me.paulschwarz:springboot4-dotenv")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
