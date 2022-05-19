package kelompoktiga.perpustakaan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kelompoktiga.perpustakaan.room.peminjaman.Peminjaman
import kelompoktiga.perpustakaan.room.peminjaman.PeminjamanDatabase
import kelompoktiga.perpustakaan.room.peminjaman.PeminjamanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelPinjam (application: Application) : AndroidViewModel(application) {
//    lateinit var allFav : LiveData<List<Favorite>>

    lateinit var repository: PeminjamanRepository


    //
    lateinit var cekData : MutableLiveData<List<Peminjaman>>

    init {
        val dao = PeminjamanDatabase.getInstance(application)?.peminjamanDao()
        repository = PeminjamanRepository(dao!!)
        cekData = MutableLiveData()

    }
    fun getLiveBukuObserver(): MutableLiveData<List<Peminjaman>> {
        return cekData
    }

    fun pinjamLive(pinjam : Peminjaman) = viewModelScope.launch(Dispatchers.IO) {
        repository.pinjamRepo(pinjam)
    }

    fun getPinjamLive(username : String) = viewModelScope.launch(Dispatchers.IO) {
        cekData.postValue(repository.getPinjamRepo(username))
    }

    fun kembaliLive(id : Int, username: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.kembaliRepo(id, username)
    }






}