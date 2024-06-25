package model.DTO

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.DAO.ServiceDAO
import java.text.Normalizer

class Services {
    companion object {
        fun getAllServices(callback: (ArrayList<Service>) -> Unit) {
            ServiceDAO.getAllServices { services ->
                callback(services)
            }
        }

        fun getServicesByLine(lineId: Int, callback: (ArrayList<Service>) -> Unit) {
            ServiceDAO.getServicesByLine(lineId) { services ->
                callback(services)
            }
        }

        fun getServicesFilteredBy(ids: List<Int>, callback: (List<Service>) -> Unit) {
            ServiceDAO.getAllServices { returnedServices ->
                callback(returnedServices.filter { ids.contains(it.lineId) })
            }
        }

        fun getServiceByVehicleId(vehicleId: Int, callback: (Service?) -> Unit) {
            ServiceDAO.getServiceByVehicleId(vehicleId) { callback(it) }
        }

        /*fun getServicesSortedByVehicle(callback: (ArrayList<Service>) -> Unit) {
            ServiceDAO.getAllServices { services ->
                val returnServices = arrayListOf<Service>()
                returnServices.addAll(services.sortedBy { it.vehicle.id }.sortedBy { it.vehicle.model })
                callback(returnServices)
            }
        }

        fun getServicesByVehicle(callback: (ArrayList<ArrayList<Service>>) -> Unit) {
            getServicesSortedByVehicle { services ->
                val servicesToReturn = arrayListOf<ArrayList<Service>>()
                var tempServices = arrayListOf<Service>()
                var precedentVehicle = ""

                services.forEach { service ->
                    if(servicesToReturn.isEmpty() && tempServices.isEmpty()) {
                        tempServices.add(service)
                        precedentVehicle = service.vehicle.model
                    }
                    else {
                        if(service.vehicle.model == precedentVehicle) {
                            tempServices.add(service)
                        }
                        else {
                            servicesToReturn.add(tempServices)
                            tempServices = arrayListOf()
                            tempServices.add(service)
                            precedentVehicle = service.vehicle.model
                        }
                    }
                }
                servicesToReturn.add(tempServices)

                callback(servicesToReturn)
            }
        }*/

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

        fun filterServicesBySearchText(services: SnapshotStateList<List<Service>>, text: String, callback: (List<List<Service>>) -> Unit) {
            val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
            fun CharSequence.unaccent(): String {
                val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                return REGEX_UNACCENT.replace(temp, "")
            }

            Lines.getAllLines { allLines ->
                callback(
                    services.map { servicesSection ->
                        servicesSection.filter { service ->
                            val line = allLines.firstOrNull { it.id == service.lineId } ?: Lines.getEmptyLine()
                            service.vehicle.parkId.contains(text.trim()) || line.name.lowercase().unaccent().contains(text.lowercase().unaccent().trim())
                        }
                    }
                )
            }
        }
    }
}