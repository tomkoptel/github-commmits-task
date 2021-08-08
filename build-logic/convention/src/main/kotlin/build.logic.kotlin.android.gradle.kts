import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

pluginManager.withPlugin("com.android.application") {
    pluginManager.withPlugin("org.jetbrains.kotlin.android") {
        applyJvmTarget8(the<AppExtension>())
    }
}
pluginManager.withPlugin("com.android.library") {
    pluginManager.withPlugin("org.jetbrains.kotlin.android") {
        applyJvmTarget8(the<LibraryExtension>())
    }
}

pluginManager.withPlugin("org.jetbrains.kotlin.android") {
    dependencies {
        // Align versions of all Kotlin components
        add("implementation", platform("org.jetbrains.kotlin:kotlin-bom"))
        // Use the Kotlin JDK 8 standard library.
        add("implementation", kotlin("stdlib-jdk8"))
    }
}

fun applyJvmTarget8(extension: BaseExtension) =
    (extension as ExtensionAware).run {
        the<KotlinJvmOptions>().run {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
