package kelompoktiga.perpustakaan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.datastore.UserManager
import kelompoktiga.perpustakaan.model.BukuAllResponseItem
import kelompoktiga.perpustakaan.room.peminjaman.Peminjaman
import kelompoktiga.perpustakaan.room.peminjaman.PeminjamanDatabase
import kelompoktiga.perpustakaan.viewmodel.ViewModelPinjam
import kotlinx.android.synthetic.bayar.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Detail : AppCompatActivity() {
    var pinjamDb : PeminjamanDatabase? = null
    lateinit var viewModel : ViewModelPinjam
    lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        userManager = UserManager(this)
        pinjamDb = PeminjamanDatabase.getInstance(this)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ViewModelPinjam::class.java)


        val detailBuku = intent.getParcelableExtra<BukuAllResponseItem>("detailbuku")
        val status = intent.getStringExtra("status")
        if (status != "home"){
            imageAddDelFavoriteDetail.text = "KEMBALIKAN"
        }

        val idBuku = detailBuku?.id
        val judul = detailBuku?.judul
        val penerbit = detailBuku?.penerbit
        val penulis = detailBuku?.penulis
        val sinopsis = detailBuku?.sinopsis
        val tanggalRilis = detailBuku?.tanggalRilis
        val sampul = detailBuku?.sampul


        tvJudulDetail.text = judul
        tvpenerbitDetail.text = penerbit
        tvpenulisDetail.text = penulis
        sinopsisDetail.text = sinopsis
        tvTglRilisDetail.text = tanggalRilis

        Glide.with(applicationContext).load(detailBuku?.sampul).into(imagebukuDetail)

        imageAddDelFavoriteDetail.setOnClickListener {
            if (imageAddDelFavoriteDetail.text.equals("PINJAM")){
                userManager.username.asLiveData().observe(this){
                    var username = it.toString()
                    if (premium.text.equals("premium")){
                        GlobalScope.launch {
                            viewModel.pinjamLive(Peminjaman(null, username, detailBuku?.id!!.toInt(), "PREMIUM", judul!!, penerbit!!, penulis!!, sampul!! , sinopsis!!, tanggalRilis!!))
                            runOnUiThread {
                                Toast.makeText(this@Detail, "Berhasil meminjam buku premium", Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        GlobalScope.launch {
                            viewModel.pinjamLive(Peminjaman(null, username, detailBuku?.id!!.toInt(), detailBuku?.tanggalPinjam.toString(),  judul!!, penerbit!!, penulis!!, sampul!! , sinopsis!!, tanggalRilis!!))
                            runOnUiThread {
                                Toast.makeText(this@Detail, "Berhasil meminjam buku gratis", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                }
            }else{
                userManager.username.asLiveData().observe(this) {
                    var username = it.toString()
                    GlobalScope.launch {
                        viewModel.kembaliLive(idBuku!!.toInt(), username)
                        runOnUiThread {
                            Toast.makeText(this@Detail, "Berhasil mengembalikan buku", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }



        }

    }

}