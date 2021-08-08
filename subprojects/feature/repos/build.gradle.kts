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

    testImplementation(project(":test-tape"))
    testImplementation(testLibs.bundles.network)
    testImplementation(testLibs.junit4)
    testImplementation(testLibs.kluent)
    testImplementation(libs.kotlinx.coroutinesCore)
    api(libs.kotlinx.coroutinesCoreJvm)
}
