import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class LocationMonitor {
    actual fun startMonitoring() {
        /*Not Needed to reproduce issue*/
    }

    actual fun stopMonitoring() {
        /*Not Needed to reproduce issue*/
    }

    private val _location = MutableStateFlow<Location?>(null)
    actual val location: StateFlow<Location?> = _location.asStateFlow()
}