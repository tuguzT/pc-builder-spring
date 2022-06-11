import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Domain
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.Exposed
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.HikariCP
import io.github.tuguzt.pcbuilder.backend.buildconfig.dependencies.QualityAssurance

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

    implementation(Exposed.core)
    implementation(Exposed.dao)
    implementation(Exposed.jdbc)

    implementation(HikariCP.dependency)

    implementation(QualityAssurance.Log.KotlinLogging.dependency)

    testImplementation(kotlin("test"))
}
