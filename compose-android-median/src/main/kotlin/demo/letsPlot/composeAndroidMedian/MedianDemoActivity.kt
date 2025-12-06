/*
 * Copyright (c) 2023 JetBrains s.r.o.
 * Use of this source code is governed by the MIT license that can be found in the LICENSE file.
 */

package demo.letsPlot.composeAndroidMedian

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ch.qos.logback.classic.android.BasicLogcatConfigurator
import demo.letsPlot.composeAndroidMedian.ui.DemoDropdownMenu
import demo.letsPlot.composeAndroidMedian.ui.DemoRadioGroup
import demo.letsPlot.composeAndroidMedian.ui.ImagePreviewDialog
import demo.letsPlot.composeAndroidMedian.util.createFigures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.letsPlot.compose.PlotFormat
import org.jetbrains.letsPlot.compose.PlotPanel
import org.jetbrains.letsPlot.compose.ggsave

class MedianDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val figures by lazy { createFigures() }

        setContent {
            val preserveAspectRatio = rememberSaveable { mutableStateOf(false) }
            val figureIndex = rememberSaveable { mutableIntStateOf(0) }

            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val savedPlotInfo = remember { mutableStateOf<Pair<Uri, PlotFormat>?>(null) }
            val filePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.CreateDocument("image/png")
            ) { uri: Uri? ->
                if (uri == null) {
                    return@rememberLauncherForActivityResult
                }
                    coroutineScope.launch {
                        try {
                            val figure = figures[figureIndex.intValue].second
                            withContext(Dispatchers.IO) {
                                ggsave(uri, figure, PlotFormat.PNG, context = context)
                            }
                            Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show()
                            // Store the Uri and the format together
                            savedPlotInfo.value = Pair(uri, PlotFormat.PNG)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, "Error saving file: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                }
            }

            savedPlotInfo.value?.let { (uri, format) ->
                ImagePreviewDialog(uri = uri, format = format) {
                    savedPlotInfo.value = null
                }
            }

            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp),
                ) {
                    @OptIn(ExperimentalLayoutApi::class)
                    FlowRow(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DemoRadioGroup(
                            preserveAspectRatio,
                        )
                        DemoDropdownMenu(
                            options = figures.unzip().first,
                            selectedIndex = figureIndex
                        )
                        Button(onClick = {
                            val suggestedFilename = "plot_${figures[figureIndex.intValue].first.lowercase()}.png"
                            filePickerLauncher.launch(suggestedFilename)
                        }, modifier = Modifier.padding(8.dp)) {
                            Text("Save")
                        }
                    }

                    PlotPanel(
                        figure = figures[figureIndex.intValue].second,
                        preserveAspectRatio = preserveAspectRatio.value,
                        modifier = Modifier.fillMaxSize()
                    ) { computationMessages ->
                        computationMessages.forEach { println("[DEMO APP MESSAGE] $it") }
                    }
                }
            }
        }
    }

    private companion object {
        init {
            BasicLogcatConfigurator.configureDefaultContext()
        }
    }
}