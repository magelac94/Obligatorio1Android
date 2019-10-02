package com.diegomedina.notesapp.model

import org.threeten.bp.ZonedDateTime

data class Note(
    val id: Int,
    val content: String,
    val createdBy: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
