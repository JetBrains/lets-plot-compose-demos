/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeDesktop.minimal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.HorizontalSplitLayout
import org.jetbrains.jewel.ui.component.rememberSplitLayoutState
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.skia.compose.PlotPanel
import org.jetbrains.letsPlot.themes.elementRect
import org.jetbrains.letsPlot.themes.theme
import java.awt.BorderLayout
import javax.swing.*

/**
 * Entry point that demonstrates embedding Lets-Plot PlotPanel into a Swing JFrame via ComposePanel (ComposePane).
 */
fun main() {
    SwingUtilities.invokeLater {
        println("compose.swing.render.on.graphics=" + System.getProperty("compose.swing.render.on.graphics"))
        println("compose.interop.blending=" + System.getProperty("compose.interop.blending"))

        val frame = JFrame("Lets-Plot in Swing via ComposePanel").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            layout = BorderLayout()
            setSize(800, 600)
            setLocationRelativeTo(null)
            contentPane.background = java.awt.Color(0x12, 0x12, 0x12)
        }

        val composePanel = ComposePanel().apply {
            background = java.awt.Color(0x12, 0x12, 0x12)
            setContent {
                val outerSplitState = rememberSplitLayoutState(initialSplitFraction = 0.5f)
                HorizontalSplitLayout(
                    state = outerSplitState,
                    first = {
                        BasicText(
                            text = "compose text",
                            modifier = Modifier.fillMaxSize(),
                            style = TextStyle(color = Color.White)
                        )
                    },
                    second = {
                        PlotPanel(
                            figure = createFigure(),
                            modifier = Modifier.fillMaxSize()
                        ) { computationMessages ->
                            computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF121212))
                        .border(1.dp, color = Color(0xFF2F2F2F)),
                    firstPaneMinWidth = 300.dp,
                    secondPaneMinWidth = 200.dp,
                )
            }
        }

        val tabs = JTabbedPane().apply {
            addTab("Plot", composePanel)
            addTab("Hello", JScrollPane(JTextArea("Swing Text Area")).apply { border = null })
        }

        frame.add(tabs, BorderLayout.CENTER)
        frame.isVisible = true
    }
}

private fun createFigure(): Figure {
    val rand = java.util.Random()
    val n = 200
    val xs = List(n) { rand.nextGaussian() }
    val data = mapOf<String, Any>(
        "x" to xs
    )

    return letsPlot(data) + geomDensity { x = "x" } + theme(
        plotBackground = elementRect(fill = "#121212", color = "#121212"),
        panelBackground = elementRect(fill = "#121212", color = "#121212")
    )
}