plugins {
    id("com.osacky.doctor")
}

doctor {
    /**
     * Throw an exception when multiple Gradle Daemons are running.
     */
    disallowMultipleDaemons.set(false)
    /**
     * Show a message if the download speed is less than this many megabytes / sec.
     */
    downloadSpeedWarningThreshold.set(.5f)
    /**
     * The level at which to warn when a build spends more than this percent garbage collecting.
     */
    GCWarningThreshold.set(0.10f)
    /**
     * By default, Gradle caches test results. This can be dangerous if tests rely on timestamps, dates, or other files
     * which are not declared as inputs.
     */
    enableTestCaching.set(true)
    /**
     * By default, Gradle treats empty directories as inputs to compilation tasks. This can cause cache misses.
     */
    failOnEmptyDirectories.set(true)
    /**
     * Do not allow building all apps simultaneously. This is likely not what the user intended.
     */
    allowBuildingAllAndroidAppsSimultaneously.set(false)
    /**
     * Warn if using Android Jetifier. It slows down builds.
     */
    warnWhenJetifierEnabled.set(true)
    /**
     * Warn when not using parallel GC. Parallel GC is faster for build type tasks and is no longer the default in Java 9+.
     */
    warnWhenNotUsingParallelGC.set(true)
    /**
     * Throws an error when the `Delete` or `clean` task has dependencies.
     * If a clean task depends on other tasks, clean can be reordered and made to run after the tasks that would produce
     * output. This can lead to build failures or just strangeness with seemingly straightforward builds
     * (e.g., gradle clean build).
     * http://github.com/gradle/gradle/issues/2488
     */
    disallowCleanTaskDependencies.set(true)

    /** Configuration properties relating to JAVA_HOME */
    javaHome {
        /**
         * Ensure that we are using JAVA_HOME to build with this Gradle.
         */
        ensureJavaHomeMatches.set(true)
        /**
         * Ensure we have JAVA_HOME set.
         */
        ensureJavaHomeIsSet.set(true)
        /**
         * Fail on any `JAVA_HOME` issues.
         */
        failOnError.set(true)
    }
}
