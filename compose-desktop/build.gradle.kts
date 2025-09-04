/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val letsPlotVersion = extra["letsPlot.version"] as String
val letsPlotKotlinVersion = extra["letsPlotKotlin.version"] as String
val letsPlotSkiaVersion = extra["letsPlotSkia.version"] as String
val composeVersion = extra["compose.version"] as String

dependencies {
    implementation(compose.desktop.currentOs)

    // Jewel UI (placeholder wrapper implemented locally to match API)
    // Keeping Compose SplitPane for underlying implementation
    implementation("org.jetbrains.compose.components:components-splitpane:$composeVersion")

    // Lets-Plot core and platform
    implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")
    implementation("org.jetbrains.lets-plot:platf-awt:$letsPlotVersion")

    // Lets-Plot Kotlin API (contains themes like themeDark())
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:$letsPlotKotlinVersion")
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:$letsPlotKotlinVersion")

    // Compose + Skia frontend for Lets-Plot
    implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotSkiaVersion")
// https://mvnrepository.com/artifact/org.jetbrains.compose.components/components-splitpane
    implementation("org.jetbrains.compose.components:components-splitpane:1.8.2")

    // Optional: Jupyter kernel helpers (kept for parity with examples)
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:$letsPlotKotlinVersion")

    implementation("org.slf4j:slf4j-simple:2.0.9")  // Enable logging to console
}
