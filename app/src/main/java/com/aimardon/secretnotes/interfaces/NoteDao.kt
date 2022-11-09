package com.aimardon.secretnotes.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aimardon.secretnotes.model.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)
    @Delete
    fun delete(note: Note)
    @Update
    fun update(note: Note)
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<Note>
}