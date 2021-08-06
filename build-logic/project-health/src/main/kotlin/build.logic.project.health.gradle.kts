import com.autonomousapps.tasks.ProjectHealthTask

plugins {
    id("com.autonomousapps.dependency-analysis")
}

dependencyAnalysis {
    issues {
        all {
            onAny {
                severity("fail")
            }
        }
    }
}

subprojects {
    tasks.matching { it.name == "check" }
        .configureEach {
            val checkTask = this
            tasks.withType<ProjectHealthTask>().forEach {
                checkTask.dependsOn(it)
            }
        }
}
