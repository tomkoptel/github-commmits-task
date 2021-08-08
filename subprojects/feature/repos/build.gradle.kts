plugins {
    id("com.android.library")
    id("build.logic.android")

    kotlin("android")
    id("build.logic.kotlin")
    id("build.logic.kotlin.android")
    id("build.logic.kotlin.checks")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    /**
     Unused dependencies which should be removed:
     - implementation("androidx.fragment:fragment-ktx:1.3.6")

     Transitively used dependencies that should be declared directly as indicated:
     - api("androidx.fragment:fragment:1.3.6")
     - api("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
     - implementation("androidx.activity:activity-ktx:1.2.2")
     - implementation("androidx.activity:activity:1.2.4")

     Existing dependencies which should be modified to be as indicated:
     - api(project(":retrofit-cache")) (was implementation)
     - api("androidx.compose.runtime:runtime:1.0.1") (was implementation)
     - api("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.0") (was testImplementation)
     - api("androidx.compose.ui:ui-tooling-preview:1.0.1") (was implementation)
     */

    api(project(":retrofit-cache"))

    api(libs.okhttp3.core)
    api(libs.retrofit2.core)
    implementation(libs.retrofit2.gson)
    implementation(libs.gson)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.activityKtx)
    api(libs.androidx.fragment)

    api(libs.androidx.lifecycleViewmodel)
    implementation(libs.androidx.lifecycleViewmodelKtx)

    implementation(libs.androidx.composeUi)
    implementation(libs.androidx.composeUiGraphics)
    implementation(libs.androidx.composeUiText)
    implementation(libs.androidx.composeUiUnit)
    api(libs.androidx.composeRuntime)
    implementation(libs.androidx.composeFoundation)
    implementation(libs.androidx.composeFoundationLayout)
    implementation(libs.androidx.composeMaterial)
    api(libs.androidx.composeUiTooling)

    testImplementation(testLibs.bundles.network)
    testImplementation(testLibs.junit4)
    testImplementation(testLibs.kluent)
    testImplementation(libs.kotlinx.coroutinesCore)
    api(libs.kotlinx.coroutinesCoreJvm)
}
