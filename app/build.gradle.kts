plugins {
    id("com.android.application")
    id("kotlin-android")
    id("build.logic.kotlin.checks")
}

val compose_version = "1.0.1"

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
        lintConfig = file("lint.xml")
        textReport = true
        textOutput("stdout")
    }
    buildFeatures {
        resValues = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    variantFilter {
        val enableBuildTypeRelease: String? by project
        val enableRelease = enableBuildTypeRelease?.toBoolean() ?: true
        val isRelease = buildType.name == "release"
        ignore = (isRelease && !enableRelease)
    }
}

dependencies {
    val accompanist_version = "0.16.0"

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))

    implementation("androidx.compose.runtime:runtime:$compose_version")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.foundation:foundation-layout:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.material:material-icons-extended:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("androidx.compose.animation:animation:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version")
    implementation("androidx.navigation:navigation-compose:2.4.0-alpha06")

    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanist_version")
    implementation("com.google.accompanist:accompanist-insets:$accompanist_version")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist_version")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.activity:activity-compose:1.3.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")

    // Add support for Java 8 Time API
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
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
