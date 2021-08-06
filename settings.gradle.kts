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
    }
}

rootProject.name = "github-commmits-task"

includeBuild("build-logic")
include("app")
