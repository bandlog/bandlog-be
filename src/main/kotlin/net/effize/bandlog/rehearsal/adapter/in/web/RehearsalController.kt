package net.effize.bandlog.rehearsal.adapter.`in`.web

import net.effize.bandlog.common.auth.AuthUser
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.AddRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.AddRehearsalSongRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.AssignMemberToInstrumentRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.CreateRehearsalRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.ModifyRehearsalRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.ModifyRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.ModifyRehearsalSongRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.AddRehearsalSongInstrumentResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.AddRehearsalSongResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.AssignMemberToInstrumentResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.CreateRehearsalResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.ModifyRehearsalResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.ModifyRehearsalSongResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.RehearsalResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.RehearsalsResponse
import net.effize.bandlog.rehearsal.application.RehearsalCommandUseCase
import net.effize.bandlog.rehearsal.application.RehearsalQueryUseCase
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rehearsal")
class RehearsalController(
    private val rehearsalQueryUseCase: RehearsalQueryUseCase,
    private val rehearsalCommandUseCase: RehearsalCommandUseCase,
) {
    @GetMapping
    fun getRehearsals(authUser: AuthUser): RehearsalsResponse {
        return rehearsalQueryUseCase.rehearsalsOf(authUser)
    }

    @GetMapping("/{rehearsalId}")
    fun getRehearsalDetail(authUser: AuthUser, @PathVariable rehearsalId: Long): RehearsalResponse {
        return rehearsalQueryUseCase.rehearsalDetail(authUser, rehearsalId)
    }

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
        return rehearsalCommandUseCase.addRehearsalSong(authUser, rehearsalId, addRehearsalSongRequest)
    }

    @PostMapping("/{rehearsalId}/song/{rehearsalSongId}/instrument")
    fun addRehearsalSongInstrument(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @RequestBody addRehearsalSongInstrumentRequest: AddRehearsalSongInstrumentRequest
    ): AddRehearsalSongInstrumentResponse {
        return rehearsalCommandUseCase.addRehearsalSongInstrument(
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
        return rehearsalCommandUseCase.assignMember(
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
        return rehearsalCommandUseCase.modifyRehearsalSong(
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
        return rehearsalCommandUseCase.modifyRehearsalSongInstrument(
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
        rehearsalCommandUseCase.deleteRehearsalSong(authUser, rehearsalId, rehearsalSongId)
    }

    @DeleteMapping("/{rehearsalId}/song/{rehearsalSongId}/instrument/{rehearsalSongInstrumentId}")
    fun deleteRehearsalSongInstrument(
        authUser: AuthUser,
        @PathVariable rehearsalId: Long,
        @PathVariable rehearsalSongId: Long,
        @PathVariable rehearsalSongInstrumentId: Long,
    ) {
        rehearsalCommandUseCase.deleteRehearsalSongInstrument(
            authUser,
            rehearsalId,
            rehearsalSongId,
            rehearsalSongInstrumentId
        )
    }
}
