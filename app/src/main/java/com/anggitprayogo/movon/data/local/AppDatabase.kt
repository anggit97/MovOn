package com.anggitprayogo.movon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anggitprayogo.movon.data.local.dao.MovieDao
import com.anggitprayogo.movon.data.local.entity.MovieEntity


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
