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
        buildConfig = true
        resValues = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    api(project(":retrofit-cache"))

    api(libs.okhttp3.core)
    implementation(libs.okhttp3.interceptor)
    api(libs.retrofit2.core)
    implementation(libs.retrofit2.gson)
    implementation(libs.gson)

    api(libs.androidx.composeUi)
    implementation(libs.androidx.composeUiGraphics)
    implementation(libs.androidx.composeUiText)
    implementation(libs.androidx.composeUiUnit)
    api(libs.androidx.composeRuntime)
    implementation(libs.androidx.composeFoundation)
    implementation(libs.androidx.composeFoundationLayout)
    implementation(libs.androidx.composeMaterial)
    implementation(libs.androidx.composeUiToolingPreview)

    implementation(libs.androidx.lifecycleViewmodelCompose)
    api(libs.androidx.lifecycleViewmodel)
    implementation(libs.androidx.lifecycleViewmodelKtx)
    implementation(libs.androidx.core)

    testImplementation(project(":test-tape"))
    testImplementation(testLibs.bundles.okreplay)
    testImplementation(testLibs.junit4)
    testImplementation(testLibs.kluent)
    testImplementation(libs.kotlinx.coroutinesCore)
    api(libs.kotlinx.coroutinesCoreJvm)

    // Add support for Java 8 Time API
    coreLibraryDesugaring(libs.androidtools.desugarJdk)
}
