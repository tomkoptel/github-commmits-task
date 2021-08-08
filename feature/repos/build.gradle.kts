plugins {
    id("com.android.library")
    kotlin("android")
    id("build.logic.android")
    id("build.logic.kotlin")
    id("build.logic.kotlin.checks")
}

android {
    buildFeatures {
        resValues = true
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    api(project(":retrofit-cache"))

    api(libs.okhttp3.core)
    api(libs.retrofit2.core)
    implementation(libs.retrofit2.gson)
    implementation(libs.gson)

    testImplementation(testLibs.bundles.network)
    testImplementation(testLibs.junit4)
    testImplementation(testLibs.kluent)
    testImplementation(libs.kotlinx.coroutinesCore)
    testImplementation(libs.kotlinx.coroutinesCoreJvm)
}
