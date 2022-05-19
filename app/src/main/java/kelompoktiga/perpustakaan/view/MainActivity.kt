package kelompoktiga.perpustakaan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.room.UserDatabase
import kelompoktiga.perpustakaan.viewmodel.ViewModelUser
import kotlinx.android.synthetic.gratis.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var userManager : kelompoktiga.perpustakaan.datastore.UserManager
    var dbUser : UserDatabase? = null
    lateinit var viewModel : ViewModelUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login()
        goToRegister()
    }

    private fun login(){
        btn_login.setOnClickListener {
            val username = masukan_username_login.text.toString()
            val password = masukan_password_login.text.toString()

            dbUser = UserDatabase.getInstance(this)
            userManager = kelompoktiga.perpustakaan.datastore.UserManager(this)

            viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ViewModelUser::class.java)
            if (username.isNotEmpty() && password.isNotEmpty()){
                viewModel.liveDataUser.observe(this, Observer {
                    if (it != 0){
                        GlobalScope.launch {
                            userManager.checkData(true)
                            userManager.saveDataUser(username, password)
                        }
                        Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Home::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Username Atau Password Salah", Toast.LENGTH_SHORT).show()
                    }
                })
                viewModel.loginLive(username, password)
            } else {
                check()
            }
        }
    }

    private fun check(){
        when {
            masukan_username_login.text.toString().isEmpty() -> {
                Toast.makeText(this, "Username Harus Di Isi", Toast.LENGTH_SHORT).show()
            }
            masukan_password_login.text.toString().isEmpty() -> {
                Toast.makeText(this, "Password Harus Di Isi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToRegister(){
        belum_punya_akun.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }
    }

}