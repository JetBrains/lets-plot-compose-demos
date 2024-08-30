/*
 * Copyright (c) 2023. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

import com.android.build.gradle.tasks.MergeSourceSetFolders
import java.net.URL

plugins {
    kotlin("android")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("com.android.application")
}

val skikoNativeX64: Configuration by configurations.creating
val skikoNativeArm64: Configuration by configurations.creating

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "demo.letsPlot"

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = "demo.letsPlot.composeMinDemo"

        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()

        versionCode = 1
        versionName = "1.0"

        ndk {
            abiFilters += listOf("x86_64", "arm64-v8a")
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }
}

val composeVersion = extra["compose.version"] as String
val androidxActivityCompose = extra["androidx.activity.compose"] as String
val skikoVersion = extra["skiko.version"] as String

val letsPlotVersion = extra["letsPlot.version"] as String
val letsPlotKotlinVersion = extra["letsPlotKotlin.version"] as String
val letsPlotSkiaVersion = extra["letsPlotSkia.version"] as String

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation("androidx.activity:activity-compose:$androidxActivityCompose")

    implementation("org.jetbrains.skiko:skiko-android:$skikoVersion")

    skikoNativeX64("org.jetbrains.skiko:skiko-android-runtime-x64:$skikoVersion")
    skikoNativeArm64("org.jetbrains.skiko:skiko-android-runtime-arm64:$skikoVersion")

    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:$letsPlotKotlinVersion")
    implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")

    implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotSkiaVersion")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("com.github.tony19:logback-android:3.0.0")
}


////////////////////////////////////////////////////////
// Include the following code in your Gradle build script
// to ensure that compatible Skiko binaries are
// downloaded and included in your project.
//
// Without this, you won't be able to run your app
// in the IDE on a device emulator.
// //////////////////////////////////////////////////////

val skikoJniLibsReleaseAssetName = "skiko-jni-libs.zip"
val skikoJniLibsDestDir = file("${project.projectDir}/src/main/jniLibs/")

tasks.register("downloadSkikoJniLibsReleaseAsset") {
    val repoUrl = "https://github.com/JetBrains/lets-plot-skia"
    val releaseTag = "v$letsPlotSkiaVersion"

    doLast {
        val downloadUrl = "$repoUrl/releases/download/$releaseTag/$skikoJniLibsReleaseAssetName"
        val outputFile = layout.buildDirectory.file("downloads/$skikoJniLibsReleaseAssetName").get().asFile

        if (outputFile.exists()) {
            println("File already exists: ${outputFile.absolutePath}")
            println("Skipping download.")
        } else {
            outputFile.parentFile?.mkdirs()

            println("Downloading $skikoJniLibsReleaseAssetName from $downloadUrl")
            URL(downloadUrl).openStream().use { input ->
                outputFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            println("Download completed: ${outputFile.absolutePath}")
        }
    }
}

tasks.register<Copy>("unzipSkikoJniLibsReleaseAsset") {
    dependsOn("downloadSkikoJniLibsReleaseAsset")
    from(zipTree(layout.buildDirectory.file("downloads/$skikoJniLibsReleaseAssetName")))
    into(skikoJniLibsDestDir)
    doFirst {
        delete(skikoJniLibsDestDir)
    }
}

tasks.register("cleanSkikoJniLibs") {
    doLast {
        delete(skikoJniLibsDestDir)
    }
}

tasks.named("clean") {
    dependsOn("cleanSkikoJniLibs")
}

tasks.withType<MergeSourceSetFolders>().configureEach {
    dependsOn("unzipSkikoJniLibsReleaseAsset")
}

////////////////////////////////////////////////////////
