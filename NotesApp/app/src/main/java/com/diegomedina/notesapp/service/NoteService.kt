package com.diegomedina.notesapp.service

import com.diegomedina.notesapp.model.Note
import retrofit2.http.GET
import retrofit2.http.Header

interface NoteService {
    @GET("notes")
    suspend fun getNotes(): List<Note>
}
