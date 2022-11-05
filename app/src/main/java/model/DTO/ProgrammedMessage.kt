package model.DTO

//import java.util.Date

enum class Severity {
    LOW, MIDDLE, IMPORTANT
}

class ProgrammedMessage(val id: Int,
                        val title: String,
                        val bodyMessage: String,
                        val lineId: Int,
                        val lastUpdated: String //TODO: Convert to Date Type
)