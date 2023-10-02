/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeDesktop.minimal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.skia.compose.PlotPanel

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Lets-Plot in Compose Desktop (min)") {
        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
            ) {

                PlotPanel(
                    figure = createFigure(),
                    modifier = Modifier.fillMaxSize()
                ) { computationMessages ->
                    computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                }
            }
        }
    }
}

private fun createFigure(): Figure {
    val rand = java.util.Random()
    val n = 200
    val xs = List(n) { rand.nextGaussian() }
    val data = mapOf<String, Any>(
        "x" to xs
    )

    return letsPlot(data) + geomDensity { x = "x" }
}

