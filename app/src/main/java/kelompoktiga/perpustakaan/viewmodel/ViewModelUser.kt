package kelompoktiga.perpustakaan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kelompoktiga.perpustakaan.room.User
import kelompoktiga.perpustakaan.room.UserDatabase
import kelompoktiga.perpustakaan.room.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelUser (application: Application) : AndroidViewModel(application) {

    var repository : UserRepository
    var liveDataUser : MutableLiveData<Int>

    init {
        val dao = UserDatabase.getInstance(application)?.userDao()
        repository = UserRepository(dao!!)
        liveDataUser = MutableLiveData()
    }

    fun registerLive(user : User) = viewModelScope.launch(Dispatchers.IO) {
        repository.registerUser(user)
    }

    fun loginLive(user : String, password : String) = viewModelScope.launch(Dispatchers.IO) {
        liveDataUser.postValue(repository.loginUser(user, password))
    }
}