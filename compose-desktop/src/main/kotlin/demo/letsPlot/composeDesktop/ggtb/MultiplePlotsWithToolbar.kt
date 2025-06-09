/*
 * Copyright (c) 2025 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeDesktop.ggtb

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.geom.geomPoint
import org.jetbrains.letsPlot.interact.ggtb
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.skia.compose.PlotPanel

fun main() = application {
    val rand = java.util.Random()
    val n = 200
    val xs = List(n) { rand.nextGaussian() }
    val ys = List(n) { rand.nextGaussian() }
    val data = mapOf<String, Any>(
        "x" to xs,
        "y" to ys
    )

    val densityPlot = letsPlot(data) + geomDensity { x = "x" }
    val scatterPlot = letsPlot(data) + geomPoint { x = "x"; y = "y" }

    Window(onCloseRequest = ::exitApplication, title = "Multiple Plot Weight Layout (Compose Desktop)") {
        MaterialTheme {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PlotPanel(
                    figure = scatterPlot + ggtb(),
                    modifier = Modifier.fillMaxHeight().weight(1f)
                ) { computationMessages ->
                    computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                }

                PlotPanel(
                    figure = densityPlot,
                    modifier = Modifier.fillMaxHeight().weight(1f)
                ) { computationMessages ->
                    computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                }
            }
        }
    }
}
