plugins {
    java
    alias(libs.plugins.spring.boot)
}

group = "io.github.darkmun"
version = "0.0.1-SNAPSHOT"
description = "MarketplacePriceTrackerBot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // bom
    implementation(platform(libs.spring.boot.bom))
    developmentOnly(platform(libs.spring.boot.bom))
    implementation(platform(libs.jackson.bom))

    // telegram bot
    implementation(libs.telegrambots.springboot.longpollingStarter)
    implementation(libs.telegrambots.client)

    // tests
    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)

    // database
    implementation(libs.spring.boot.starter.dataJPA)
    implementation(libs.spring.boot.starter.flyway)
    runtimeOnly(libs.flyway.database.postgresql)
    runtimeOnly(libs.postgresql)

    // .env file support
    developmentOnly(platform(libs.spring.dotenv.bom))
    developmentOnly(libs.springboot4.dotenv)

    // other
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.configurationProcessor)
    implementation(libs.lombok)
    developmentOnly(libs.spring.boot.dockerCompose)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
