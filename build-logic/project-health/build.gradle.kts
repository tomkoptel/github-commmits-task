plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(pluginLibs.dependencyAnalysis)
    implementation(pluginLibs.doctor)
}
