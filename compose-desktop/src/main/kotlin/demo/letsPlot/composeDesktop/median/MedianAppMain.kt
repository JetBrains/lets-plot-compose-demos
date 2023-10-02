/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */


package demo.letsPlot.composeDesktop.median

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import demo.letsPlot.composeDesktop.median.ui.DemoList
import demo.letsPlot.composeDesktop.median.util.createFigures
import org.jetbrains.letsPlot.skia.compose.PlotPanel

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Lets-Plot in Compose Desktop (median)") {

        val figures = createFigures()

        val preserveAspectRatio = remember { mutableStateOf(false) }
        val figureIndex = remember { mutableStateOf(0) }

        MaterialTheme {
            Row {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(CenterVertically)
                        .width(IntrinsicSize.Max)
                ) {
                    Text("Plots:", fontWeight = FontWeight.Bold)
                    DemoList(
                        options = figures.unzip().first,
                        selectedIndex = figureIndex,
                    )
                    Row {
                        Text(
                            text = "Keep ratio:",
                            modifier = Modifier
                                .align(CenterVertically)
                        )
                        Checkbox(preserveAspectRatio.value, onCheckedChange = { preserveAspectRatio.value = it })
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
                    ) {
                        PlotPanel(
                            figure = figures[figureIndex.value].second,
                            preserveAspectRatio = preserveAspectRatio.value,
                            modifier = Modifier.fillMaxSize()
                        ) { computationMessages ->
                            computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                        }
                    }
                }
            }
        }
    }
}

