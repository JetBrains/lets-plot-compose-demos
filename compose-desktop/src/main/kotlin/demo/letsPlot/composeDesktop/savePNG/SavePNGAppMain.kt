/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeDesktop.savePNG

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.skia.compose.PlotPanel
import org.jetbrains.skia.Image
import java.io.File
import java.util.*

fun main() = application {
    var showImageWindow by remember { mutableStateOf(false) }
    var savedFilePath by remember { mutableStateOf("") }
    var savedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    Window(onCloseRequest = ::exitApplication, title = "Lets-Plot in Compose Desktop (min)") {
        MaterialTheme {
            val figure = createFigure()

            Column(
                modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
            ) {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val filename = "plot_${System.currentTimeMillis()}.png"
                                val actualFilePath = ggsave(figure, filename)
                                val file = File(actualFilePath)

                                // Load the saved image
                                val imageBytes = file.readBytes()
                                val skiaImage = Image.makeFromEncoded(imageBytes)
                                val imageBitmap = skiaImage.toComposeImageBitmap()

                                // Update UI state from the background thread
                                savedFilePath = actualFilePath
                                savedImage = imageBitmap
                                showImageWindow = true

                                println("Plot saved as: $actualFilePath")
                            } catch (e: Exception) {
                                println("Error saving plot: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text("Save PNG")
                }

                PlotPanel(
                    figure = figure,
                    modifier = Modifier.fillMaxSize()
                ) { computationMessages ->
                    computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                }
            }
        }
    }

    if (showImageWindow) {
        Window(
            onCloseRequest = { showImageWindow = false },
            title = "Saved Plot"
        ) {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Plot saved successfully!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "File path: $savedFilePath",
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    savedImage?.let { image ->
                        Image(
                            bitmap = image,
                            contentDescription = "Saved plot",
                            modifier = Modifier.size(400.dp, 300.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { showImageWindow = false }) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

private fun createFigure(): Figure {
    val rand = Random()
    val n = 200
    val xs = List(n) { rand.nextGaussian() }
    val data = mapOf<String, Any>(
        "x" to xs
    )

    return letsPlot(data) + geomDensity { x = "x" }
}
