package kelompoktiga.perpustakaan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.room.User
import kelompoktiga.perpustakaan.room.UserDatabase
import kelompoktiga.perpustakaan.viewmodel.ViewModelUser
import kotlinx.android.synthetic.gratis.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {
    var dbUser : UserDatabase? = null
    lateinit var viewModel : ViewModelUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register()
        goToLogin()

    }

    private fun register(){
        btn_Register.setOnClickListener {
            val username = masukan_username_Register.text.toString()
            val email = masukan_email_Register.text.toString()
            val password = masukan_password_Register.text.toString()
            val konfirmasi = masukan_konfpassword_Register.text.toString()

            dbUser = UserDatabase.getInstance(this)
            viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ViewModelUser::class.java)

            if (password == konfirmasi){
                GlobalScope.launch {
                    viewModel.registerLive(User(null, username, email, password))
                    runOnUiThread{
                        Toast.makeText(this@Register, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Register, MainActivity::class.java))
                    }
                }
            } else {
                check()
            }
        }
    }

    private fun check(){
        when {
            masukan_username_Register.text.toString().isEmpty() -> {
                Toast.makeText(this, "Username Harus Di Isi", Toast.LENGTH_SHORT).show()
            }
            masukan_email_Register.text.toString().isEmpty() -> {
                Toast.makeText(this, "Email Harus Di Isi", Toast.LENGTH_SHORT).show()
            }
            masukan_password_Register.text.toString().isEmpty() -> {
                Toast.makeText(this, "Password Harus Di Isi", Toast.LENGTH_SHORT).show()
            }
            masukan_konfpassword_Register.text.toString().isEmpty() -> {
                Toast.makeText(this, "Konfirmasi Password Harus Di Isi", Toast.LENGTH_SHORT).show()
            }
            masukan_password_Register.text.toString() != masukan_password_Register.text.toString() -> {
                Toast.makeText(this, "Password & Konfirmasi Password Harus Sama", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToLogin(){
        sudah_punya_akun.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}