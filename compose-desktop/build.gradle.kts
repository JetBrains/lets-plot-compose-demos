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
val composeVersion = extra["compose.version"] as String

dependencies {
    implementation(compose.desktop.currentOs)

    // Guard against accidental Compose module version drift: pin key artifacts to compose.version
    constraints {
        implementation("org.jetbrains.compose.runtime:runtime:$composeVersion")
        implementation("org.jetbrains.compose.ui:ui:$composeVersion")
        implementation("org.jetbrains.compose.foundation:foundation:$composeVersion")
    }

//    // Split pane API (ExperimentalSplitPaneApi, HorizontalSplitPane, rememberSplitPaneState)
//    implementation("org.jetbrains.compose.components:components-splitpane:$composeVersion")

    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-kernel:$letsPlotKotlinVersion")
    implementation("org.jetbrains.lets-plot:lets-plot-common:$letsPlotVersion")
    implementation("org.jetbrains.lets-plot:platf-awt:$letsPlotVersion")

    implementation("org.jetbrains.lets-plot:lets-plot-compose:$letsPlotSkiaVersion")

    implementation("org.slf4j:slf4j-simple:2.0.9")  // Enable logging to console
}
