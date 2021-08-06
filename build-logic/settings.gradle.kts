rootProject.name = "build-logic"

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("pluginLibs") {
            from(files("$rootDir/../gradle/pluginLibs.versions.toml"))
        }
    }
}

include("kotlin-static-checks")
include("project-health")
