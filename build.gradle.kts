buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.android.tools.build:gradle:7.0.0")
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
