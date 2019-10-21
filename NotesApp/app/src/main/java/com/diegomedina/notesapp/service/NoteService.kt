package com.diegomedina.notesapp.service

import com.diegomedina.notesapp.model.Note
import com.diegomedina.notesapp.service.request.SigninRequest
import com.diegomedina.notesapp.service.request.AddNoteRequest
import com.diegomedina.notesapp.service.response.SuccessReponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NoteService {
    @GET("todos")
    suspend fun getNotes(): List<Note>

    @POST("todos")
    suspend fun addNotes(@Body addNoteRequest: AddNoteRequest): SuccessReponse

}
