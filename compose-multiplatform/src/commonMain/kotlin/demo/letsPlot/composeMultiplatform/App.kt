/*
 * Copyright (c) 2025 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeMultiplatform

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.compose.PlotPanel
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.intern.Plot
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.themes.*
import kotlin.random.Random

@Composable
fun App() {
    val baseFigure = remember { createFigure() }
    var selectedTheme by remember { mutableStateOf("minimal2") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val themes = mapOf(
        "minimal2" to themeMinimal2(),
        "bw" to themeBW(),
        "grey" to themeGrey(),
        "classic" to themeClassic(),
        "light" to themeLight(),
        "minimal" to themeMinimal(),
        "void" to themeVoid(),
        "none" to themeNone()
    )

    val themedFigure = themes[selectedTheme]?.let { theme ->
        (baseFigure as Plot) + theme
    } ?: baseFigure

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text("Theme:")
                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { dropdownExpanded = true }
                ) {
                    Text(selectedTheme)
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    themes.keys.forEach { themeName ->
                        DropdownMenuItem(
                            onClick = {
                                selectedTheme = themeName
                                dropdownExpanded = false
                            }
                        ) {
                            Text(themeName)
                        }
                    }
                }
            }

            PlotPanel(
                figure = themedFigure,
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