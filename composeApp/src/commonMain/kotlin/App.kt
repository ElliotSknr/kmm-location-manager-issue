import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    // In reality this would sit in some sort of view/screen model
    val locationMonitor = remember { LocationMonitor() }
    val location by locationMonitor.location.collectAsState()

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource("compose-multiplatform.xml"),
                null
            )
            Text(text = "Location Monitor")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = locationMonitor::startMonitoring) {
                    Text(text = "Start Monitoring")
                }
                Button(onClick = locationMonitor::stopMonitoring) {
                    Text(text = "Stop Monitoring")
                }
            }
            Text(text = "Location: $location")
        }
    }
}