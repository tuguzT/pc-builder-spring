enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "PCBuilderSpringBackend"
include(":domain")
project(":domain").projectDir = file("../PC Builder/domain")
