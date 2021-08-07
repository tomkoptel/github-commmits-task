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
    implementation(project(":retrofit-cache"))

    implementation(libs.bundles.network)
    implementation(libs.kotlinx.coroutinesCore)

    testImplementation(testLibs.bundles.network)
    testImplementation(testLibs.bundles.engine)
}
