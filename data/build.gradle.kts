import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.*

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
    implementation(Domain.dependency) { isChanging = true }

    implementation(kotlin("stdlib"))

    implementation(Koin.core)

    implementation(Exposed.core)
    implementation(Exposed.dao)
    implementation(Exposed.jdbc)

    implementation(HikariCP.dependency)

    implementation(QualityAssurance.Log.KotlinLogging.dependency)

    testImplementation(kotlin("test"))
}
