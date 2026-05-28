/*
 * Copyright (c) 2026. JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeAndroidMedian.util

import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.annotations.layerLabels
import org.jetbrains.letsPlot.geom.*
import org.jetbrains.letsPlot.label.ggtitle
import org.jetbrains.letsPlot.label.xlab
import org.jetbrains.letsPlot.label.ylab
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.scale.scaleXDateTime
import org.jetbrains.letsPlot.themes.themeVoid
import org.jetbrains.letsPlot.tooltips.layerTooltips
import kotlin.time.Instant

fun createFigures(): List<Pair<String, Figure>> {
    return listOf(
        "Density" to densityPlot(),
        "Bar" to barPlot(),
        "Pie" to piePlot(),
        "Datetime" to datetimePlot(),
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

fun piePlot(): Figure {
    val data = mapOf(
        "name" to listOf('b', 'd', 'a', 'e', 'c'),
        "value" to listOf(90, 50, 40, 20, 10)
    )
    return letsPlot(data) +
            geomPie(stat = Stat.identity, size = 0.8, sizeUnit = "min", labels = layerLabels().line("@name: ^slice"), showLegend = false) {
                slice = "value"
                fill = "name"
            } + themeVoid()
}

private fun datetimePlot(): Figure {
    val data = mapOf(
        "instant" to listOf(
            Instant.parse("2026-05-01T00:00:00Z"),
            Instant.parse("2026-05-01T06:00:00Z"),
            Instant.parse("2026-05-01T12:00:00Z"),
            Instant.parse("2026-05-01T18:00:00Z"),
            Instant.parse("2026-05-02T00:00:00Z"),
            Instant.parse("2026-05-02T06:00:00Z"),
        ),
        "throughput" to listOf(18.0, 24.0, 21.5, 28.0, 25.0, 31.0),
    )

    return letsPlot(data) {
        x = "instant"
        y = "throughput"
    } +
            geomLine(
                color = "#4C78A8",
                size = 1.6,
                tooltips = layerTooltips()
                    .line("@instant|@throughput req/s")
                    .format("instant", "%Y-%m-%d %H:%M UTC"),
            ) +
            geomPoint(
                color = "#E45756",
                size = 4.0,
                tooltips = layerTooltips()
                    .line("@instant|@throughput req/s")
                    .format("instant", "%Y-%m-%d %H:%M UTC"),
            ) +
            scaleXDateTime(format = "%b %d\n%H:%M") +
            ggtitle("kotlinx.datetime.Instant values in plot") +
            xlab("Instant (UTC)") +
            ylab("Throughput")
}
