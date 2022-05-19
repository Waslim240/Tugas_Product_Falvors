package kelompoktiga.perpustakaan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kelompoktiga.perpustakaan.model.BukuAllResponseItem
import kelompoktiga.perpustakaan.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelBuku : ViewModel() {

    var liveDataBuku : MutableLiveData<List<BukuAllResponseItem>?> = MutableLiveData()

//================================= LIVE DATA BUKU ================================================//
    fun getLiveBukuObserver() : MutableLiveData<List<BukuAllResponseItem>?> {
        return liveDataBuku
    }


//================================= API CLIENT BUKU ===============================================//
    fun makeApiFilm(){
        ApiClient.instance.getAllBuku()
            .enqueue(object : Callback<List<BukuAllResponseItem>> {
                override fun onResponse(
                    call: Call<List<BukuAllResponseItem>>,
                    response: Response<List<BukuAllResponseItem>>
                ) {
                    when {
                        response.isSuccessful -> {
                            liveDataBuku.postValue(response.body())
                        }
                        else -> {
                            liveDataBuku.postValue(null)
                        }
                    }
                }

                override fun onFailure(call: Call<List<BukuAllResponseItem>>, t: Throwable) {
                    liveDataBuku.postValue(null)
                }
            })
    }

}