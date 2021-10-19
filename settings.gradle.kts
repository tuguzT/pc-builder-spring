enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "PC_Builder_Spring_Backend"
include(":domain")
project(":domain").projectDir = file("../PC Builder/domain")
