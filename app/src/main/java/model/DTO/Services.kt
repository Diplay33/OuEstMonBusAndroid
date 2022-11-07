package model.DTO

import model.DAO.ServiceDAO

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
                    if(service.lineId == 132 || service.lineId == 133 || service.lineId == 134 ||
                        service.lineId == 135 || service.lineId == 136 || service.lineId == 137 ||
                        service.lineId == 138 || service.lineId == 139 || service.lineId == 140 ||
                        service.lineId == 141 || service.lineId == 142 || service.lineId == 143 ||
                        service.lineId == 144 || service.lineId == 145 || service.lineId == 146 ||
                        service.lineId == 147 || service.lineId == 148 || service.lineId == 149 ||
                        service.lineId == 150 || service.lineId == 151 || service.lineId == 152 ||
                        service.lineId == 153 || service.lineId == 154 || service.lineId == 155 ||
                        service.lineId == 156 || service.lineId == 157 || service.lineId == 158 ||
                        service.lineId == 159 || service.lineId == 160 || service.lineId == 161 ||
                        service.lineId == 162 || service.lineId == 163 || service.lineId == 164 ||
                        service.lineId == 166 || service.lineId == 167 || service.lineId == 168 ||
                        service.lineId == 169 || service.lineId == 170 || service.lineId == 171 ||
                        service.lineId == 172 || service.lineId == 173 || service.lineId == 174 ||
                        service.lineId == 175 || service.lineId == 176 || service.lineId == 177 ||
                        service.lineId == 178 || service.lineId == 179 || service.lineId == 180 ||
                        service.lineId == 181 || service.lineId == 182 || service.lineId == 186 ||
                        service.lineId == 187 || service.lineId == 188 || service.lineId == 189 ||
                        service.lineId == 190 || service.lineId == 191 || service.lineId == 192 ||
                        service.lineId == 193 || service.lineId == 194 || service.lineId == 195 ||
                        service.lineId == 196 || service.lineId == 197 || service.lineId == 198 ||
                        service.lineId == 199) {
                        navetteTramServices.add(service)
                    }
                }
                callback(navetteTramServices)
            }
        }

        fun getServicesSortedByVehicle(callback: (ArrayList<Service>) -> Unit) {
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
        }
    }
}