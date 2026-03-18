/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

val composeVersion = extra["compose.version"] as String
val letsPlotVersion = extra["letsPlot.version"] as String
val letsPlotKotlinVersion = extra["letsPlotKotlin.version"] as String
val letsPlotComposeVersion = extra["letsPlotCompose.version"] as String

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.compose.components:components-resources:$composeVersion")

    // Lets-Plot Kotlin API
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:$letsPlotKotlinVersion")
    // Lets-Plot JVM
    implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")
    // Optional: contains the PlotImageExport utility which enables exporting to raster formats.
    implementation("org.jetbrains.lets-plot:platf-awt:$letsPlotVersion")
//    implementation("org.jetbrains.lets-plot:canvas:$letsPlotVersion")
//    implementation("org.jetbrains.lets-plot:plot-raster:$letsPlotVersion")
//    // Lets-Plot 'image export' (optional - enables exporting to raster formats)
//    implementation("org.jetbrains.lets-plot:lets-plot-image-export:$letsPlotVersion")
    // Lets-Plot Compose UI
    implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotComposeVersion")

    implementation("org.slf4j:slf4j-simple:2.0.17")  // Enable logging to console
}
