import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.BaseExtension

pluginManager.withPlugin("com.android.application") {
    applyConventions(the<AppExtension>())
}
pluginManager.withPlugin("com.android.library") {
    applyConventions(the<LibraryExtension>())
}

fun applyConventions(extension: BaseExtension) = extension.run {
    setCompileSdkVersion(30)

    defaultConfig {
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    lintOptions {
        isWarningsAsErrors = true
        textReport = true
        textOutput("stdout")
    }

    variantFilter {
        val enableBuildTypeRelease: String? by project
        val enableRelease = enableBuildTypeRelease?.toBoolean() ?: true
        val isRelease = buildType.name == "release"
        ignore = (isRelease && !enableRelease)
    }
}
