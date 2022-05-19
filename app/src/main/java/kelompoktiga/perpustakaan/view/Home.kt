package kelompoktiga.perpustakaan.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.adapter.AdapterBuku
import kelompoktiga.perpustakaan.datastore.UserManager
import kelompoktiga.perpustakaan.viewmodel.ViewModelBuku
import kotlinx.android.synthetic.gratis.activity_home.*

@SuppressLint("SetTextI18n, NotifyDataSetChanged")
class Home : AppCompatActivity() {

    lateinit var userManager : UserManager
    lateinit var adapterBuku : AdapterBuku

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initRecycler()
        getDataBuku()
        getDataStore()

        profileGo.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }


    private fun getDataStore(){
        userManager = UserManager(this)

        userManager.username.asLiveData().observe(this){
            welcome_username.text = "Welcome, $it"
        }
    }

// ===================================== MENAMPILKAN LIST BUKU =====================================//
    private fun initRecycler(){
        rvBuku.layoutManager = LinearLayoutManager(this)
    adapterBuku = AdapterBuku() {
            val pdh = Intent(this, Detail::class.java)
            pdh.putExtra("detailbuku", it)
            startActivity(pdh)
        }

        rvBuku.adapter = adapterBuku
    }

    private fun getDataBuku(){
        val viewModel = ViewModelProvider(this).get(ViewModelBuku::class.java)

        viewModel.getLiveBukuObserver().observe(this, Observer {
            when {
                it != null -> {
                    adapterBuku.setDataFilm(it)
                    adapterBuku.notifyDataSetChanged()
                }
            }
        })

        viewModel.makeApiFilm()
    }

}