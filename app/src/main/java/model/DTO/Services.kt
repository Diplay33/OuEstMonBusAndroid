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

        fun getNavetteTramServices(callback: (ArrayList<Service>) -> Unit) {
            ServiceDAO.getAllServices { services ->
                val navetteTramServices = ArrayList<Service>()
                services.forEach { service ->
                    if(service.lineId in 123..198) {
                        navetteTramServices.add(service)
                    }
                }
                callback(navetteTramServices)
            }
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
            return services.sortedBy { it.vehicle.parkId }.sortedBy { it.vehicle.model }
        }

        fun filterServicesByVehicle(services: List<Service>): List<List<Service>> {
            val filteredServices = filterServicesSortedByVehicle(services)
            val servicesToReturn = mutableListOf<List<Service>>()
            var tempServices = mutableListOf<Service>()
            var precedentVehicle = ""

            filteredServices.forEach { service ->
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
                        tempServices = mutableListOf()
                        tempServices.add(service)
                        precedentVehicle = service.vehicle.model
                    }
                }
            }
            servicesToReturn.add(tempServices)

            return servicesToReturn
        }

        fun filterServicesBySearchText(services: SnapshotStateList<List<Service>>, text: String): List<List<Service>> {
            val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
            fun CharSequence.unaccent(): String {
                val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
                return REGEX_UNACCENT.replace(temp, "")
            }

            return services.map { it.filter { it.vehicle.parkId.contains(text.trim()) ||
                    Lines.getLine(it.lineId.toString()).lineName.lowercase().unaccent().contains(text.lowercase().unaccent().trim()) } }
        }
    }
}