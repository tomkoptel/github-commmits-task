plugins {
    id("com.android.application")
    id("kotlin-android")
    id("build.logic.kotlin.checks")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.olderwold.jlabs.github"
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        getByName("main").java.srcDirs(
            "src/main/kotlin",
            "src/main/kotlinX"
        )
        getByName("androidTest").java.srcDirs(
            "src/androidTest/kotlin"
        )
        getByName("test").java.srcDirs(
            "src/test/kotlin",
            "src/test/kotlinX"
        )
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
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    lint {
        isWarningsAsErrors = true
        lintConfig = file("lint.xml")
        textReport = true
        textOutput("stdout")
    }
    buildFeatures {
        resValues = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    variantFilter {
        val enableBuildTypeRelease: String? by project
        val enableRelease = enableBuildTypeRelease?.toBoolean() ?: true
        val isRelease = buildType.name == "release"
        ignore = (isRelease && !enableRelease)
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        // Treat all Kotlin warnings as errors (disabled by default)
        val warningsAsErrors: String? by project
        allWarningsAsErrors = warningsAsErrors.toBoolean()

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            // Enable experimental coroutines APIs, including Flow
            "-Xopt-in=kotlin.Experimental",
        )

        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.Experimental"

        // Set JVM target to 1.8
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
