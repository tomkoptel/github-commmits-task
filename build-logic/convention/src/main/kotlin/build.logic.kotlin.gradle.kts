import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
