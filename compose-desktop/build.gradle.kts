/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

val letsPlotVersion = extra["letsPlot.version"] as String
val letsPlotKotlinVersion = extra["letsPlotKotlin.version"] as String
val letsPlotSkiaVersion = extra["letsPlotSkia.version"] as String

dependencies {
    implementation(compose.desktop.currentOs)

    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:$letsPlotKotlinVersion")
    implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")
    implementation("org.jetbrains.lets-plot:platf-awt:$letsPlotVersion")

    implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotSkiaVersion")

    implementation("org.slf4j:slf4j-simple:2.0.9")  // Enable logging to console
}
