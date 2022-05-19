package kelompoktiga.perpustakaan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import kelompoktiga.perpustakaan.R
import kelompoktiga.perpustakaan.datastore.UserManager

class Splash : AppCompatActivity() {
    lateinit var userManager : UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        userManager = UserManager(this)
        check()
    }

    private fun check(){
        Handler(Looper.getMainLooper()).postDelayed({
            userManager.boolean.asLiveData().observe(this){
                if (it == true){
                    startActivity(Intent(this, Home::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }, 2000)
    }
}