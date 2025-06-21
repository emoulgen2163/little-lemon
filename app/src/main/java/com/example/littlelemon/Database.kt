package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Query
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity
data class MenuItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)


@Dao
interface MenuDao {
    @Query(value = " select * from MenuItem ")
    fun getAll(): LiveData<List<MenuItem>>

    @Insert
    fun insertAll(vararg menuItem: MenuItem)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItem) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItem::class], version = 1)
abstract class MenuDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
}

