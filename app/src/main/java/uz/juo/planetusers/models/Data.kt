package uz.juo.planetusers.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Data(
    val avatar: String="",
    val email: String="",
    val first_name: String="",
    @PrimaryKey
    val id: Int=0,
    val last_name: String=""
):Serializable