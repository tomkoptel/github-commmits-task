plugins {
    id("com.android.application")
    id("kotlin-android")
    id("build.logic.kotlin.checks")
    id("build.logic.android")
    id("build.logic.android.kotlin")
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
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))

    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.accompanist)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)

    // Add support for Java 8 Time API
    coreLibraryDesugaring(libs.androidtools.desugarJdk)
}
