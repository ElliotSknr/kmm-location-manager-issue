import kotlinx.coroutines.flow.StateFlow

expect class LocationMonitor() {
    fun startMonitoring()
    fun stopMonitoring()
    val location: StateFlow<Location?>
}

data class Location(
    val latitude: Double,
    val longitude: Double,
    /**
     * Time - Reported as seconds since 1970
     */
    val time: Long
) {
    override fun toString(): String {
        return "Location(latitude: $latitude, longitude: $longitude, time: $time)"
    }
}