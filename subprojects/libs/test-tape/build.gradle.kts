plugins {
    id("build.logic.jvm")
    id("build.logic.kotlin")
    id("build.logic.kotlin.checks")
}

dependencies {
    implementation(testLibs.junit4)
    implementation(testLibs.bundles.network)
}
