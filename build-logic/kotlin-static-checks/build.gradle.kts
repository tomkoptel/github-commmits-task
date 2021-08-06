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
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.0-RC3")
}
