import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBestForNavigation
import platform.Foundation.NSLog
import platform.Foundation.timeIntervalSince1970
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
actual class LocationMonitor {
    private val _clLocationManager = CLLocationManager()

    private class LocationDelegate(
        private val onLocationCallback: (CLLocation?) -> Unit,
    ) : NSObject(), CLLocationManagerDelegateProtocol {
        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            // This method stops getting invoked after around 5 updates are sent
            val location = (didUpdateLocations.lastOrNull()) as CLLocation?
            onLocationCallback(location)
        }
        /*Other override methods here are also not called e.g. didFailWithError*/
    }

    private val locationDelegate = LocationDelegate(
        onLocationCallback = { location ->
            val newLocation = location?.let {
                it.coordinate.useContents { Location(latitude, longitude, it.timestamp.timeIntervalSince1970.toLong()) }
            }
            NSLog("Got a new location value: $newLocation")
            _location.value = newLocation
        }
    )

    init {
        _clLocationManager.delegate = locationDelegate
        _clLocationManager.requestWhenInUseAuthorization()
        _clLocationManager.pausesLocationUpdatesAutomatically = false
        _clLocationManager.distanceFilter = kCLDistanceFilterNone
        _clLocationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation
    }

    actual fun startMonitoring() {
        _clLocationManager.startUpdatingLocation()
    }

    actual fun stopMonitoring() {
        _clLocationManager.stopUpdatingLocation()
    }

    private val _location = MutableStateFlow<Location?>(null)
    actual val location: StateFlow<Location?> = _location.asStateFlow()
}