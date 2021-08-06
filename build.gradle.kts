buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val pluginLibs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("pluginLibs")
            as org.gradle.accessors.dm.LibrariesForPluginLibs
        classpath(pluginLibs.kotlin)
        classpath(pluginLibs.android)
    }
}

plugins {
    id("build.logic.project.health")
}

allprojects {
    group = "com.olderwold.jlabs.github"
    version = "1.0"

    repositories {
        google()
        mavenCentral()
    }
}
