package com.diegomedina.notesapp.controller

import com.diegomedina.notesapp.service.NoteService
import com.diegomedina.notesapp.service.request.AddNoteRequest


class NoteController {
    private val noteService = RetrofitController.retrofit.create(NoteService::class.java)

    suspend fun getAllNotes() = noteService.getNotes()

    suspend fun addNote(priority: String, description: String, due_date: String, completed: Boolean, category_id: Int ) {
        val request = AddNoteRequest(priority, description, due_date, completed, category_id)
        noteService.addNotes(request)
    }
}
