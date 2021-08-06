plugins {
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

ktlint {
    debug.set(false)
    version.set("0.42.0")
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
        include("**/kotlin/**")
    }
}

detekt {
    reports {
        html {
            enabled = true
            destination = file("build/reports/detekt.html")
        }
    }
}

val ktlint = tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.KtLintCheckTask>()
val detekt = tasks.withType<io.gitlab.arturbosch.detekt.Detekt>()
tasks.matching { it.name.contains("check") }
    .configureEach {
        this.dependsOn(ktlint)
        this.dependsOn(detekt)
    }

detekt.configureEach {
    exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
}
