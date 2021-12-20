plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "io.github.tuguzt.pcbuilder.backend"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
    // Domain layer
    implementation("com.github.tuguzT:pc_builder_domain:main-SNAPSHOT") {
        isChanging = true
    }

    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    // Kotlin extensions
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    runtimeOnly("com.h2database:h2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // Documentation
    implementation("org.springdoc:springdoc-openapi-ui:1.6.2")
    implementation("org.springdoc:springdoc-openapi-security:1.6.2")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.2")

    // Third-Party
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.16")
    implementation("io.insert-koin:koin-core:3.1.4")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.bouncycastle:bcprov-jdk15on:1.69")

    // Testing
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("com.h2database:h2")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "1.8"
        }
    }
    test {
        useJUnitPlatform()
    }
    named<Jar>("jar") {
        enabled = false
    }
}
