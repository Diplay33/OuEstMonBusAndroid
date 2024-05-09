package model.DTO

class Path(val id: Int,
           val name: String,
           val direction: String,
           val coordinates: List<List<List<Double>>>
) {
    fun getDestinationName(): String {
        var destinationName = ""
        var writeInName = false

        name.forEach { char ->
            if(writeInName) {
                destinationName += char
            }

            if(char == "-".first()) {
                writeInName = true
            }
        }

        return destinationName.trim()
    }
}