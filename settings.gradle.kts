/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val composeVersion = extra["compose.version"] as String
        val agpVersion = extra["agp.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)

        id("org.jetbrains.compose").version(composeVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        // LP-Skia local-dev repo.
        maven {
            url = uri("/Users/Igor/Work/lets-plot-skia/.maven-publish-dev-repo")
        }

        // SNAPSHOTS
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")

        mavenLocal()
    }
}

include("compose-desktop")
