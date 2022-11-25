package model.DTO

import java.time.LocalDateTime
import java.time.ZonedDateTime

enum class Severity {
    LOW, MIDDLE, IMPORTANT
}

class ProgrammedMessage(val id: Int,
                        val title: String,
                        val bodyMessage: String,
                        val lineId: Int,
                        val lastUpdatedRaw: String,
                        val lastUpdated: LocalDateTime = ZonedDateTime
                            .parse(lastUpdatedRaw)
                            .toLocalDateTime(),
                        val severity: Severity
)