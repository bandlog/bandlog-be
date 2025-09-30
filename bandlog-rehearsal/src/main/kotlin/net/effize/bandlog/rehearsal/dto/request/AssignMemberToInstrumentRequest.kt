package net.effize.bandlog.rehearsal.dto.request

data class AssignMemberToInstrumentRequest(
    val teamId: Long,
    val memberId: Long
)