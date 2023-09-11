package model.DTO

class NSchedulesMapMarker(val type: NSchedulesMapMarkerType,
                          val stop: Station?,
                          val service: Service?
)

enum class NSchedulesMapMarkerType {
    STOP, VEHICLE
}