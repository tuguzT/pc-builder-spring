import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Domain
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Koin
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Ktor
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.QualityAssurance

val ktorVersion = "2.0.2"

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

group = "io.github.tuguzt.pcbuilder.backend"
version = "0.0.1"

application {
    mainClass.set("io.github.tuguzt.pcbuilder.backend.ApplicationKt")

    val isDevelopment = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
    implementation(Domain.dependency) {
        isChanging = true
    }

    implementation(kotlin("stdlib"))

    implementation(Ktor.Server.core)
    implementation(Ktor.Server.engineNetty)
    implementation(Ktor.Server.Http.contentNegitiation)
    implementation(Ktor.Server.Serialization.kotlinX)
    implementation(Ktor.Server.Security.auth)
    implementation(Ktor.Server.Security.authJwt)
    implementation(Ktor.Server.StatusPages.dependency)

    implementation(Koin.core)
    implementation(Koin.Ktor.dependency)

    implementation(QualityAssurance.Log.Ktor.slf4jLogger)
    implementation(QualityAssurance.Log.Logback.dependency)

    testImplementation(kotlin("test"))
    testImplementation(QualityAssurance.UnitTest.Ktor.Server.dependency)
    testImplementation(QualityAssurance.UnitTest.Koin.dependency)
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "11"
        }
    }
}
