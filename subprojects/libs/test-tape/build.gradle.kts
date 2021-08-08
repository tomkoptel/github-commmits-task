plugins {
    id("build.logic.jvm")
    id("build.logic.kotlin")
    id("build.logic.kotlin.checks")
}

dependencies {
    api(testLibs.junit4)
    api(testLibs.okreplay.core)
    implementation(testLibs.okreplay.junit)
}
