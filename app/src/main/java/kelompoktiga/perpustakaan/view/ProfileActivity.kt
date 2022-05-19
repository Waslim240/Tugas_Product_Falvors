package kelompoktiga.perpustakaan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.adapter.PinjamAdapter
import kelompoktiga.perpustakaan.datastore.UserManager
import kelompoktiga.perpustakaan.model.BukuAllResponseItem
import kelompoktiga.perpustakaan.room.peminjaman.PeminjamanDatabase
import kelompoktiga.perpustakaan.viewmodel.ViewModelPinjam
import kotlinx.android.synthetic.gratis.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    var pinjamDb : PeminjamanDatabase? = null
    lateinit var viewModel : ViewModelPinjam
    lateinit var userManager : UserManager
    lateinit var bookAdapter : PinjamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        pinjamDb = PeminjamanDatabase.getInstance(this)
        userManager = UserManager(this)


        var username = ""
        userManager.username.asLiveData().observe(this){
            username = it
            getAllPinjaman(username)
        }

    }


    fun getAllPinjaman(username : String){
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ViewModelPinjam::class.java)


        viewModel.getLiveBukuObserver().observe(this@ProfileActivity){
            if (it != null){
                rvBuku2.layoutManager = LinearLayoutManager(this@ProfileActivity)
                bookAdapter = PinjamAdapter (){
                    val pindah = Intent(this@ProfileActivity, Detail::class.java)
                    val detailBuku : BukuAllResponseItem = BukuAllResponseItem("asdasd", it.idBuku.toString(),it.judul, it.penerbit,it.penulis,it.sampul,
                        it.sinopsis, 0, it.tanggalRilis)
                    pindah.putExtra("detailbuku", detailBuku)
                    startActivity(pindah)
                }
                rvBuku2.adapter = bookAdapter
                bookAdapter.setDataFilm(it)
                bookAdapter.notifyDataSetChanged()
            }else{

            }

        }
        viewModel.getPinjamLive(username)


    }

}