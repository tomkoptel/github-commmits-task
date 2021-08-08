plugins {
    `java-library`
    kotlin("jvm")
    id("build.logic.kotlin")
    id("build.logic.kotlin.checks")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(libs.okhttp3.core)
    implementation(libs.retrofit2.core)
}
