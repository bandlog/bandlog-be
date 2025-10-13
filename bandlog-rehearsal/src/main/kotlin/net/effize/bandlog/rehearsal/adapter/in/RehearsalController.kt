package net.effize.bandlog.rehearsal.adapter.`in`

import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.request.AssignMemberToInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.CreateRehearsalRequest
import net.effize.bandlog.rehearsal.dto.request.ModifyRehearsalRequest
import net.effize.bandlog.rehearsal.dto.request.ModifyRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.ModifyRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongInstrumentResponse
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongResponse
import net.effize.bandlog.rehearsal.dto.response.AssignMemberToInstrumentResponse
import net.effize.bandlog.rehearsal.dto.response.CreateRehearsalResponse
import net.effize.bandlog.rehearsal.dto.response.ModifyRehearsalResponse
import net.effize.bandlog.rehearsal.dto.response.ModifyRehearsalSongResponse
import net.effize.bandlog.rehearsal.service.RehearsalCommandUseCase
import net.effize.bandlog.rehearsal.service.RehearsalSongCommandUseCase
import net.effize.bandlog.rehearsal.service.RehearsalSongInstrumentCommandUseCase
import net.effize.bandlog.shared.auth.AuthUser
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rehearsal")
class RehearsalController(
    private val rehearsalCommandUseCase: RehearsalCommandUseCase,
    private val rehearsalSongCommandUseCase: RehearsalSongCommandUseCase,
    private val rehearsalSongInstrumentCommandUseCase: RehearsalSongInstrumentCommandUseCase
) {

    @PostMapping
    fun createRehearsal(authUser: AuthUser, createRehearsalRequest: CreateRehearsalRequest): CreateRehearsalResponse {
        return rehearsalCommandUseCase.createRehearsal(authUser, createRehearsalRequest)
    }

    @PostMapping("/{rehearsalId}/song")
    fun addRehearsalSong(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @RequestBody addRehearsalSongRequest: AddRehearsalSongRequest
    ): AddRehearsalSongResponse {
        return rehearsalSongCommandUseCase.addRehearsalSong(authUser, rehearsalId, addRehearsalSongRequest)
    }

    @PostMapping("/{rehearsalId}/song/{rehearsalSongId}/instrument")
    fun addRehearsalSongInstrument(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @RequestBody addRehearsalSongInstrumentRequest: AddRehearsalSongInstrumentRequest
    ): AddRehearsalSongInstrumentResponse {
        return rehearsalSongInstrumentCommandUseCase.addRehearsalSongInstrument(
            authUser,
            rehearsalId,
            rehearsalSongId,
            addRehearsalSongInstrumentRequest
        )
    }

    @PutMapping("/{rehearsalId}/song/{rehearsalSongId}/instrument/{rehearsalSongInstrumentId}/member")
    fun assignMember(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @PathVariable rehearsalSongInstrumentId: Long,
        @RequestBody assignMemberRequest: AssignMemberToInstrumentRequest
    ): AssignMemberToInstrumentResponse {
        return rehearsalSongInstrumentCommandUseCase.assignMember(
            authUser,
            rehearsalId,
            rehearsalSongId,
            rehearsalSongInstrumentId,
            assignMemberRequest
        )
    }

    @PutMapping("/{rehearsalId}")
    fun modifyRehearsal(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @RequestBody modifyRehearsalRequest: ModifyRehearsalRequest
    ): ModifyRehearsalResponse {
        return rehearsalCommandUseCase.modifyRehearsal(
            authUser,
            rehearsalId,
            modifyRehearsalRequest
        )
    }

    @PutMapping("/{rehearsalId}/song/{rehearsalSongId}")
    fun modifyRehearsalSong(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @RequestBody modifyRehearsalSongRequest: ModifyRehearsalSongRequest
    ): ModifyRehearsalSongResponse {
        return rehearsalSongCommandUseCase.modifyRehearsalSong(
            authUser,
            rehearsalId,
            rehearsalSongId,
            modifyRehearsalSongRequest
        )
    }

    @PutMapping("/{rehearsalId}/song/{rehearsalSongId}/instrument/{rehearsalSongInstrumentId}")
    fun modifyRehearsalSongInstrument(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @PathVariable rehearsalSongInstrumentId: Long,
        @RequestBody modifyRehearsalSongInstrumentRequest: ModifyRehearsalSongInstrumentRequest
    ): ModifyRehearsalSongResponse {
        return rehearsalSongInstrumentCommandUseCase.modifyRehearsalSongInstrument(
            authUser,
            rehearsalId,
            rehearsalSongId,
            rehearsalSongInstrumentId,
            modifyRehearsalSongInstrumentRequest
        )
    }

    @DeleteMapping("/{rehearsalId}")
    fun deleteRehearsal(authUser: AuthUser, @PathVariable rehearsalId: Long) {
        rehearsalCommandUseCase.deleteRehearsal(authUser, rehearsalId)
    }

    @DeleteMapping("/{rehearsalId}/song/{rehearsalSongId}")
    fun deleteRehearsalSong(authUser: AuthUser, @PathVariable rehearsalId: Long, @PathVariable rehearsalSongId: Long) {
        rehearsalSongCommandUseCase.deleteRehearsalSong(authUser, rehearsalId, rehearsalSongId)
    }

    @DeleteMapping("/{rehearsalId}/song/{rehearsalSongId}/instrument/{rehearsalSongInstrumentId}")
    fun deleteRehearsalSongInstrument(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @PathVariable rehearsalSongInstrumentId: Long,
    ) {
        rehearsalSongInstrumentCommandUseCase.deleteRehearsalSongInstrument(
            authUser,
            rehearsalId,
            rehearsalSongId,
            rehearsalSongInstrumentId
        )
    }
}