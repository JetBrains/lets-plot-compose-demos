/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform").apply(false)
    kotlin("jvm").apply(false)
    kotlin("android").apply(false)
    id("com.android.application").apply(false)
    id("org.jetbrains.compose").apply(false)
}

allprojects {
    group = "org.jetbrains.lets-plot"
    version = "1.0.0-SNAPSHOT"
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    tasks.withType<JavaCompile>().all {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}
