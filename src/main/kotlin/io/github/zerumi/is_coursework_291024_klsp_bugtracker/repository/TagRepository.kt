package io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long>
