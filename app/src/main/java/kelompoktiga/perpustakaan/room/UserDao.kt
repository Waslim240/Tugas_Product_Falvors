package kelompoktiga.perpustakaan.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun userRegister(user: User) : Long

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username2 AND password = :password2) ")
    fun userLogin(username2 : String, password2 : String) : Int

    @Query("SELECT * FROM User")
    fun allUser() : List<User>
}