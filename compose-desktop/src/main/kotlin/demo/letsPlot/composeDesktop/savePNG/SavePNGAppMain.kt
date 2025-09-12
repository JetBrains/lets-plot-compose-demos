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
import androidx.compose.runtime.*
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
    var imageWindows by remember { mutableStateOf<List<ImageWindowData>>(emptyList()) }

    Window(onCloseRequest = ::exitApplication, title = "Lets-Plot in Compose Desktop (min)") {
        MaterialTheme {
            val figure = remember { createFigure() }

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

                                val imageBytes = file.readBytes()
                                val skiaImage = Image.makeFromEncoded(imageBytes)
                                val imageBitmap = skiaImage.toComposeImageBitmap()

                                // Do state change to trigger recomposition
                                imageWindows = imageWindows + ImageWindowData(
                                    id = System.currentTimeMillis(),
                                    filePath = actualFilePath,
                                    imageBitmap = imageBitmap
                                )

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

    // Render image windows
    imageWindows.forEach { windowData ->
        ImageWindow(
            windowData = windowData,
            onClose = {
                // Do state change to trigger recomposition
                imageWindows = imageWindows.filter { it.id != windowData.id }
            }
        )
    }
}

data class ImageWindowData(
    val id: Long,
    val filePath: String,
    val imageBitmap: ImageBitmap,
)

@Composable
private fun ImageWindow(
    windowData: ImageWindowData,
    onClose: () -> Unit
) {
    Window(
        onCloseRequest = onClose,
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
                    text = "File path: ${windowData.filePath}",
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    bitmap = windowData.imageBitmap,
                    contentDescription = "Saved plot",
                    modifier = Modifier.size(400.dp, 300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onClose) {
                    Text("Close")
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
