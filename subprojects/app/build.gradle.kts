import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("com.android.application")
    id("build.logic.android")

    kotlin("android")
    id("build.logic.kotlin")
    id("build.logic.kotlin.android")
    id("build.logic.kotlin.checks")
}

android {
    defaultConfig {
        applicationId = "com.olderwold.jlabs.github"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        // We use a bundled debug keystore, to allow debug builds from CI to be upgradable
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName(this.name)
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        lintConfig = file("lint.xml")
    }

    buildFeatures {
        resValues = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        unitTests.all { test ->
            test.testLogging {
                showStackTraces = true
                showExceptions = true
                showCauses = true
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}

dependencies {
    implementation(project(":feature-repos"))
    implementation(project(":feature-details"))

    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.androidx.navigation)

    implementation(libs.accompanist.insets)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)

    implementation(libs.androidx.composeMaterialIcons)

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.fragmentKtx)

    testImplementation(testLibs.bundles.okreplay)
    testImplementation(testLibs.junit4)
    testImplementation(testLibs.kluent)
    testImplementation(testLibs.androidx.coreTesting)
    testImplementation(testLibs.bundles.mockk)
    testImplementation(testLibs.android.robolectric)
    testImplementation(testLibs.android.barista)
    testImplementation(testLibs.android.junit)
    // See https://issuetracker.google.com/issues/122321150?pli=1
    debugImplementation(testLibs.android.fragmentTesting)
}
