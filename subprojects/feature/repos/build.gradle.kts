plugins {
    id("com.android.library")
    id("build.logic.android")

    kotlin("android")
    id("build.logic.kotlin")
    id("build.logic.kotlin.android")
    id("build.logic.kotlin.checks")
}

dependencies {
    api(project(":retrofit-cache"))

    api(libs.okhttp3.core)
    api(libs.retrofit2.core)
    implementation(libs.retrofit2.gson)
    implementation(libs.gson)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("io.arrow-kt:arrow-core:0.13.2")

    testImplementation(testLibs.bundles.network)
    testImplementation(testLibs.junit4)
    testImplementation(testLibs.kluent)
    testImplementation(libs.kotlinx.coroutinesCore)
    testImplementation(libs.kotlinx.coroutinesCoreJvm)
}
