package model.DTO

import android.content.Context
import model.DAO.ServiceDAO
import java.text.Normalizer

class Services {
    companion object {
        fun getAllServices(network: String, callback: (ArrayList<Service>) -> Unit ) {
            when(network) {
                "tbm" -> ServiceDAO.getAllTBMServices { callback(it) }
                "ametis", "star", "corolis", "tam", "met", "kiceo", "irigo", "filbleu" -> ServiceDAO.getAllServicesFromGTFSRT(
                    network = network,
                ) {
                    callback(it)
                }
                "" -> callback(arrayListOf())
            }
        }

        fun getServicesByLine(
            network: String,
            lineId: Int,
            callback: (ArrayList<Service>) -> Unit
        ) {
            when(network) {
                "tbm" ->
                    ServiceDAO.getTBMServicesByLine(lineId) { callback(it) }
                "ametis", "star", "corolis", "tam", "met", "kiceo", "irigo", "filbleu" ->
                    ServiceDAO.getServicesFromGTFSRT(lineId, network) { callback(ArrayList(it)) }
                "" -> callback(arrayListOf())
            }
        }

        fun getServicesFilteredBy(
            network: String,
            ids: List<Int>,
            callback: (List<Service>) -> Unit
        ) {
            when(network) {
                "tbm" ->
                    ServiceDAO.getAllTBMServices { returnedServices ->
                        callback(returnedServices.filter { ids.contains(it.lineId) })
                    }
                "ametis", "star", "corolis", "tam", "met", "kiceo", "irigo", "filbleu" ->
                    ServiceDAO.getAllServicesFromGTFSRT(network) { returnedServices ->
                        callback(returnedServices.filter { ids.contains(it.lineId) })
                    }
                "" -> callback(listOf())
            }
        }

        fun getServiceByVehicleId(vehicleId: Int, callback: (Service?) -> Unit) {
            ServiceDAO.getTBMServiceByVehicleId(vehicleId) { callback(it) }
        }

        private fun filterServicesSortedByVehicle(services: List<Service>): List<Service> {
            return services.sortedBy { it.vehicle.parkId }.sortedBy { it.vehicle.fullName }
        }

        fun filterServicesByVehicle(services: List<Service>): List<List<Service>> {
            val filteredServices = filterServicesSortedByVehicle(services)
            val servicesToReturn = mutableListOf<List<Service>>()
            var tempServices = mutableListOf<Service>()
            var precedentVehicle = ""

            filteredServices.forEach { service ->
                if(servicesToReturn.isEmpty() && tempServices.isEmpty()) {
                    tempServices.add(service)
                    precedentVehicle = service.vehicle.fullName
                }
                else {
                    if(service.vehicle.fullName == precedentVehicle) {
                        tempServices.add(service)
                    }
                    else {
                        servicesToReturn.add(tempServices)
                        tempServices = mutableListOf()
                        tempServices.add(service)
                        precedentVehicle = service.vehicle.fullName
                    }
                }
            }
            servicesToReturn.add(tempServices)

            return servicesToReturn
        }

        fun filterServicesBySearchText(services: ArrayList<List<Service>>, text: String): List<List<Service>> {
            val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
            fun CharSequence.unaccent(): String {
                val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                return REGEX_UNACCENT.replace(temp, "")
            }

            return services.map { servicesSection ->
                servicesSection.filter { service ->
                    val line = Lines.getAllLines().firstOrNull { it.id == service.lineId } ?: Lines.getEmptyLine()
                    val processedText = text.lowercase().unaccent().trim()
                    service.vehicle.parkId.contains(processedText)
                            || line.name.lowercase().unaccent().contains(processedText)
                            || (service.vehicle.brand + " " + service.vehicle.model).lowercase().unaccent().contains(processedText)
                }
            }
        }
    }
}