plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("com.autonomousapps:dependency-analysis-gradle-plugin:0.75.0")
}
