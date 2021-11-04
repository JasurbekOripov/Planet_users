package uz.juo.planetusers.room.dao

import androidx.room.*
import uz.juo.planetusers.room.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(userEntity: UserEntity)

    @Query("delete from planet_users_table where id=:id")
    fun deleteById(id: Int)

    @Query("select * from planet_users_table")
    fun getAll(): List<UserEntity>

    @Query("select * from planet_users_table where id=:id")
    fun getById(id: Int): UserEntity
}