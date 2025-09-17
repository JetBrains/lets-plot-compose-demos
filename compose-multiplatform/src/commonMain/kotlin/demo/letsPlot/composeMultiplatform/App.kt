/*
 * Copyright (c) 2025 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeMultiplatform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.compose.PlotPanel
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot
import kotlin.random.Random

@Composable
fun App() {
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

fun createFigure(): Figure {
    val rand = Random.Default
    val n = 200
    val xs = List(n) { rand.nextDouble() * 6 - 3 } // Normal-like distribution
    val data = mapOf<String, Any>(
        "x" to xs
    )

    return letsPlot(data) + geomDensity { x = "x" }
}