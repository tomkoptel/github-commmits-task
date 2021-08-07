pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    includeBuild("build-logic")
}

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("pluginLibs") {
            from(files("$rootDir/gradle/pluginLibs.versions.toml"))
        }
        create("testLibs") {
            from(files("$rootDir/gradle/testLibs.versions.toml"))
        }
    }
}

rootProject.name = "github-commmits-task"

includeBuild("build-logic")
include("app")
subproject(name = "feature-repos", path = file("feature/repos"))
subproject(name = "retrofit-cache", path = file("libs/retrofit-cache"))

/**
 * Helper function that uses name as artifact name and accepts the relative path to point out the sources location.
 */
fun subproject(name: String, path: File) {
    include(":$name")
    project(":$name").projectDir = path
}
