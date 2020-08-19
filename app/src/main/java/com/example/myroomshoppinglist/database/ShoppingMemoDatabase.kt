package com.example.myroomshoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
    Klasse abstract um nicht alles implementieren zu müssen
    entities = arrayOf(ShoppingMemo::class)-> Welche entities had die DB
    version = 1 -> Version (siehe DBHelper aus altem Projekt)

 */

@Database(
    entities = arrayOf(ShoppingMemo::class),
    version = 1
)
abstract class ShoppingMemoDatabase : RoomDatabase() {

    abstract fun shoppingMemoDao() : ShoppingMemoDAO
    // Weitere DAOs....

    object Factory{

        private var instance: ShoppingMemoDatabase? = null

        fun getInstance(context: Context): ShoppingMemoDatabase {
            if(instance == null) {
                // Sperrt für überzählige Threads
                synchronized(ShoppingMemoDatabase::class) {
                    if(instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                ShoppingMemoDatabase::class.java,
                                "shoppingmemo_database")
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}