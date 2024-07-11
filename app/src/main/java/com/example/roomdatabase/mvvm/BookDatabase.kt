package com.example.roomdatabase.mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDoa

    companion object {
        @Volatile
        private var INSTANS: BookDatabase? = null


        fun getDatebase(context: Context): BookDatabase {
            val tampleInstans = INSTANS
            if (tampleInstans != null) {
                return tampleInstans
            }
            synchronized(this) {
                val instans = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "table_name"
                ).build()
                INSTANS = instans
                return instans
            }
        }
    }
}