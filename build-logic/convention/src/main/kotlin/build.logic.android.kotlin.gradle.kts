import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.android.extensions")
}

tasks.withType<KotlinCompile>().configureEach {
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

pluginManager.withPlugin("com.android.application") {
    applyConventions(the<AppExtension>())
}
pluginManager.withPlugin("com.android.library") {
    applyConventions(the<LibraryExtension>())
}

fun applyConventions(extension: BaseExtension) = extension.run {
    the<KotlinJvmOptions>().run {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

