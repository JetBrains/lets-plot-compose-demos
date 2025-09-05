package demo.letsPlot.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.qos.logback.classic.android.BasicLogcatConfigurator

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { startActivity(Intent(this@MainMenuActivity, MinimalDemoActivity::class.java)) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text("Open Minimal Demo")
                    }

                    Button(
                        onClick = { startActivity(Intent(this@MainMenuActivity, MedianDemoActivity::class.java)) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text("Open Median Demo")
                    }

                    Button(
                        onClick = { startActivity(Intent(this@MainMenuActivity, RedrawDemoActivity::class.java)) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text("Open Redraw Demo")
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