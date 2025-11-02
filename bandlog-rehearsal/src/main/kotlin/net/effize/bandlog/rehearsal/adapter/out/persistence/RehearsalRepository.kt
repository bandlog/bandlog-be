package net.effize.bandlog.rehearsal.adapter.out.persistence

import net.effize.bandlog.rehearsal.domain.entity.Rehearsal
import org.springframework.data.jpa.repository.JpaRepository

interface RehearsalRepository : JpaRepository<Rehearsal, Long>