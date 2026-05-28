/*
 * Copyright (c) 2026. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    kotlin("android")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("com.android.application")
}

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

val androidxActivityCompose = extra["androidx.activity.compose"] as String

val letsPlotVersion = extra["letsPlot.version"] as String
val letsPlotKotlinVersion = extra["letsPlotKotlin.version"] as String
val letsPlotComposeVersion = extra["letsPlotCompose.version"] as String

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation("androidx.activity:activity-compose:$androidxActivityCompose")

    // Lets-Plot Kotlin API
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:$letsPlotKotlinVersion")
    // Lets-Plot Multiplatform
    implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")
    implementation("org.jetbrains.lets-plot:canvas:$letsPlotVersion")
    implementation("org.jetbrains.lets-plot:plot-raster:$letsPlotVersion")
    // Lets-Plot Compose UI
    implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotComposeVersion")

    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.github.tony19:logback-android:3.0.0")
}
