package kelompoktiga.perpustakaan.room

class UserRepository(private val dao: UserDao) {

    suspend fun registerUser(user : User){
        dao.userRegister(user)
    }

    suspend fun loginUser(user : String, password : String) : Int{
        return dao.userLogin(user, password)
    }

}