package com.diegomedina.notesapp.controller

import com.diegomedina.notesapp.service.NoteService

class NoteController {
    private val noteService = RetrofitController.retrofit.create(NoteService::class.java)

    suspend fun getAllNotes() = noteService.getNotes()
}
