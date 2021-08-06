plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") {
        content {
            includeGroup("org.jetbrains.kotlinx")
        }
    }
    mavenCentral()
}

dependencies {
    implementation(pluginLibs.ktlint)
    implementation(pluginLibs.detekt)
}
