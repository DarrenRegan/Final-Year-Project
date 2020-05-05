package com.example.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AdminActivity : AppCompatActivity() {

    private lateinit var CategoryName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Toast.makeText(this, "Welcome to Admin Panel", Toast.LENGTH_SHORT).show()

        CategoryName = getIntent().getExtras()?.get("category").toString()
        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show()
    }
}
