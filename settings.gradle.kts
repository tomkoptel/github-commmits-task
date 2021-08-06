enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    includeBuild("build-logic")
}
rootProject.name = "github-commmits-task"

includeBuild("build-logic")
include("app")
