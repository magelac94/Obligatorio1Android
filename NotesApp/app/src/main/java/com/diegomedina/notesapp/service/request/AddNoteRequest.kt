package com.diegomedina.notesapp.service.request

data class AddNoteRequest(
    val priority: String,
    val description: String,
    val due_date: String,
    val completed: Boolean,
    val category_id: Int
)
