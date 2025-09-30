package net.effize.bandlog.rehearsal.repository

import net.effize.bandlog.rehearsal.model.Rehearsal
import org.springframework.data.jpa.repository.JpaRepository

interface RehearsalRepository : JpaRepository<Rehearsal, Long>