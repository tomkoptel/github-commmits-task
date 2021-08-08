plugins {
    id("build.logic.jvm")
    id("build.logic.kotlin")
    id("build.logic.kotlin.checks")
}

dependencies {
    api(libs.okhttp3.core)
    implementation(libs.retrofit2.core)
}
