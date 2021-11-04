package uz.juo.planetusers.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.juo.planetusers.room.dao.UserDao
import uz.juo.planetusers.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): UserDao
}