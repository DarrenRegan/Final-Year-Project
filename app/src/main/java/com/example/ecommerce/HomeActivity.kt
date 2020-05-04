package com.example.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import io.paperdb.Paper

class HomeActivity : AppCompatActivity() {

    private lateinit var LogoutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        LogoutButton = findViewById(R.id.logout_btn)

        LogoutButton.setOnClickListener{
            //Destroys user data from memory
            Paper.book().destroy()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
