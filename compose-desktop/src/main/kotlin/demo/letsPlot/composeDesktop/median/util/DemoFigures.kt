/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeDesktop.median.util

import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot

fun createFigures(): List<Pair<String, Figure>> {
    return listOf(
        "Density" to densityPlot(),
        "Bar" to barPlot(),
    )
}

private fun densityPlot(): Figure {
    val rand = java.util.Random()
    val n = 200
    val xs = List(n) { rand.nextGaussian() }
    val data = mapOf<String, Any>(
        "x" to xs
    )

    return letsPlot(data) + geomDensity { x = "x" }
}

fun barPlot(): Figure {
    val data = mapOf(
        "time" to listOf("Lunch", "Lunch", "Dinner", "Dinner", "Dinner")
    )

    return letsPlot(data) +
            geomBar(alpha = 0.5) {
                x = "time"
                color = "time"
                fill = "time"
            }
}
