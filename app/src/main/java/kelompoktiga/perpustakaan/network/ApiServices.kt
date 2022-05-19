package kelompoktiga.perpustakaan.network

import kelompoktiga.perpustakaan.model.BukuAllResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    // get data film
    @GET("buku")
    fun getAllBuku() : Call<List<BukuAllResponseItem>>

}