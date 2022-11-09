package com.aimardon.secretnotes.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aimardon.secretnotes.interfaces.NoteDao
import com.aimardon.secretnotes.model.Note

@Database(entities = [Note::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    object DataBaseBuilder {
        private var instanse: DataBase? = null
        fun getDataBase(context: Context): DataBase? {
            when {
                instanse == null -> synchronized(DataBase::class.java) {
                    instanse = buildDataBase(context)
                }
            }
            return instanse
        }
    fun buildDataBase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DataBase::class.java,
            "note_base"
        ).build()
    }


}