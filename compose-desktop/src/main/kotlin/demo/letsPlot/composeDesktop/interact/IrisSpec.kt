/*
 * Copyright (c) 2024 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeDesktop.interact

import demo.letsPlot.composeDesktop.data.Iris
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.geom.geomPoint
import org.jetbrains.letsPlot.gggrid
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.intern.figure.SubPlotsFigure
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.scale.scaleYContinuous
import org.jetbrains.letsPlot.themes.themeBW

class IrisSpec {
    fun scatter(): Plot {
        return letsPlot(Iris.map()) {
            x = "sepal length (cm)"
            y = "sepal width (cm)"
        } + geomPoint(
            size = 5,
            alpha = 0.4
        ) + themeBW()
    }

    fun density(): Plot {
        return letsPlot(Iris.map()) {
            x = "sepal length (cm)"
        } + geomDensity(
            size = 1.5,
            alpha = 0.1
        ) + scaleYContinuous(position = "right")
    }

    fun pair(): SubPlotsFigure {
        return gggrid(
            plots = listOf(scatter(), density()),
            ncol = 2,
            sharex = "row"
        )
    }
}