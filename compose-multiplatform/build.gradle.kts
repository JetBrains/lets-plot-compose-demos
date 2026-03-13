/*
 * Copyright (c) 2025 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("com.android.application")
}

val composeVersion = extra["compose.version"] as String
val letsPlotVersion = extra["letsPlot.version"] as String
val letsPlotKotlinVersion = extra["letsPlotKotlin.version"] as String
val letsPlotComposeVersion = extra["letsPlotCompose.version"] as String
val activityComposeVersion = findProperty("androidx.activity.compose") as String

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.compose.runtime:runtime:$composeVersion")
                implementation("org.jetbrains.compose.foundation:foundation:$composeVersion")
                implementation("org.jetbrains.compose.material:material:$composeVersion")
                implementation("org.jetbrains.compose.ui:ui:$composeVersion")

                // Lets-Plot Kotlin API
                implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:$letsPlotKotlinVersion")
                // Lets-Plot Multiplatform
                implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")
                implementation("org.jetbrains.lets-plot:canvas:$letsPlotVersion")
                implementation("org.jetbrains.lets-plot:plot-raster:$letsPlotVersion")
                // Lets-Plot Compose UI
                implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotComposeVersion")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:$activityComposeVersion")

                // Android logging
                implementation("org.slf4j:slf4j-api:2.0.17")
                implementation("com.github.tony19:logback-android:3.0.0")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.compose.components:components-resources:$composeVersion")

                implementation("org.jetbrains.lets-plot:platf-awt:$letsPlotVersion")
                implementation("org.slf4j:slf4j-simple:2.0.17")
            }
        }

    }
}

android {
    namespace = "demo.letsPlot.composeMultiplatform"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()

    defaultConfig {
        applicationId = "demo.letsPlot.composeMultiplatform"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "demo.letsPlot.composeMultiplatform.MainKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "lets-plot-compose-multiplatform-demo"
            packageVersion = "1.0.0"
        }
    }
}