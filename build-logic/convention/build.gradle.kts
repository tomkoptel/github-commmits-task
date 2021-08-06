plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(pluginLibs.android)
    implementation(pluginLibs.kotlin)
}
