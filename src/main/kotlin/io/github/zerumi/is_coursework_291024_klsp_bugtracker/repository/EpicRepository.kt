package io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Epic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EpicRepository : JpaRepository<Epic, Long>
