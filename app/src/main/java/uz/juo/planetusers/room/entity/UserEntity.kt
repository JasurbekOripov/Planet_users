package uz.juo.planetusers.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "planet_users_table")
data class UserEntity(
    val avatar: String = "",
    val email: String = "",
    val first_name: String = "",
    @PrimaryKey
    val id: Int = 0,
    val last_name: String = ""
):Serializable