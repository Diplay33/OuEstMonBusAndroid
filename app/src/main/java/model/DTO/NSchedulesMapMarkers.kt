package model.DTO

class NSchedulesMapMarkers {
    companion object {
        fun retrieveVehicles(lineId: Int, serviceIds: List<Int>, callback: (List<NSchedulesMapMarker>) -> Unit) {
            Services.getServicesByLine("tbm", lineId) { services ->
                val filteredServices = services.filter { serviceIds.contains(it.vehicle.id.toInt()) }
                callback(filteredServices.map { NSchedulesMapMarker(NSchedulesMapMarkerType.VEHICLE, null, it) })
            }
        }
    }
}