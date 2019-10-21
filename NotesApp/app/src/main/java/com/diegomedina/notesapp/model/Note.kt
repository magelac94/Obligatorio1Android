package com.diegomedina.notesapp.model

import org.threeten.bp.ZonedDateTime

data class Note(
    val priority: String,
    val description: String,
    val due_date: ZonedDateTime,
    val completed: Boolean,
    val category_id: Int
)
