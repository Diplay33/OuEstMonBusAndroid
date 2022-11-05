package model.DTO

import model.DAO.ServiceDAO

class Services {
    companion object {
        fun getServicesByLine(lineId: Int, callback: (ArrayList<Service>) -> Unit) {
            ServiceDAO.getServicesByLine(lineId) { services ->
                callback(services)
            }
        }

        fun getNavetteTramServices(callback: (ArrayList<Service>) -> Unit) {
            ServiceDAO.getAllServices { services ->
                val navetteTramServices = ArrayList<Service>()
                services.forEach { service ->
                    if(service.line == 132 || service.line == 133 || service.line == 134 ||
                        service.line == 135 || service.line == 136 || service.line == 137 ||
                        service.line == 138 || service.line == 139 || service.line == 140 ||
                        service.line == 141 || service.line == 142 || service.line == 143 ||
                        service.line == 144 || service.line == 145 || service.line == 146 ||
                        service.line == 147 || service.line == 148 || service.line == 149 ||
                        service.line == 150 || service.line == 151 || service.line == 152 ||
                        service.line == 153 || service.line == 154 || service.line == 155 ||
                        service.line == 156 || service.line == 157 || service.line == 158 ||
                        service.line == 159 || service.line == 160 || service.line == 161 ||
                        service.line == 162 || service.line == 163 || service.line == 164 ||
                        service.line == 166 || service.line == 167 || service.line == 168 ||
                        service.line == 169 || service.line == 170 || service.line == 171 ||
                        service.line == 172 || service.line == 173 || service.line == 174 ||
                        service.line == 175 || service.line == 176 || service.line == 177 ||
                        service.line == 178 || service.line == 179 || service.line == 180 ||
                        service.line == 181 || service.line == 182 || service.line == 186 ||
                        service.line == 187 || service.line == 188 || service.line == 189 ||
                        service.line == 190 || service.line == 191 || service.line == 192 ||
                        service.line == 193 || service.line == 194 || service.line == 195 ||
                        service.line == 196 || service.line == 197 || service.line == 198 ||
                        service.line == 199) {
                        navetteTramServices.add(service)
                    }
                }
                callback(navetteTramServices)
            }
        }
    }
}