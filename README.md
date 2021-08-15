# App

![Android CI with Gradle](https://github.com/tomkoptel/github-commmits-task/workflows/Android%20CI%20with%20Gradle/badge.svg)

The aim of this test assignment is to build an App that connects to the Github API, shows the public repositories of a particular user and then retrieves their respective last commits.

<img src="https://github.com/tomkoptel/github-commmits-task/blob/develop/app.gif?raw=true" width="432px" height="768px" alt="App In Action"/>

# Quality Assurance
## CI
The following projects relies on [Github Actions](https://github.com/features/actions).
As part of each new pull request we do execute `./gradlew check` and build debug version of application.

## Static Checks
The following project relies on the [ktlint](https://github.com/pinterest/ktlint) and [detekt](https://github.com/detekt/detekt).
The plugins configured under the composite build in [build-logic/kotlin-static-checks](./build-logic/kotlin-static-checks).
The plugin allows us to reapply checks to any future module or composite build.

You can find the latest version inside [Actions Tab](https://github.com/tomkoptel/github-commmits-task/actions).

# Architecture
The application architecture based on [the clean architecture](https://www.freecodecamp.org/news/a-quick-introduction-to-clean-architecture-990c014448d2/).

The app relies on the vertical organisation of the source code.
The vertical organisation reflected on the package structure that follows:

- $companyPackage.feature.X.data (holds impl of use cases + schema used to pull data)
- $companyPackage.feature.X.domain (represents plain old Kotlin objects and pure interfaces)
- $companyPackage.feature.X.ui (represents UI layer)

The app packages:

- $companyPackage.feature.repos (implementation of list of repose)
- $companyPackage.feature.details (implementation of details page that shows commits by closest year commited)
- $companyPackage.retrofit (represents additional capability used to add support for caching)
- $companyPackage.tape (adds DSL to execute integration tests for network layer relying on OkReplay lib)

# Libraries
Networking stack
- [Gson](https://github.com/google/gson)
- [OkHttp](https://square.github.io/okhttp/)
- [Retrofit](https://square.github.io/retrofit/)

UI stack
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Compose Paging](https://developer.android.com/jetpack/androidx/releases/paging)

Miscellaneous:
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)

Unit Testing
- [Mockk](https://github.com/mockk/mockk)
- [Junit 4](https://junit.org/junit4/)
- [Kluent](https://github.com/MarkusAmshove/Kluent)

# Dev Tools
- Android Studio Arctic Fox | 2020.3.1 (Build #AI-203.7717.56.2031.7583922, built on July 26, 2021)
- Kotlin 1.5.1
- [Android Gradle Plugin 7.0.0](https://developer.android.com/studio/releases/gradle-plugin)
- [Gradle 7.1.1](https://gradle.org/releases/)
