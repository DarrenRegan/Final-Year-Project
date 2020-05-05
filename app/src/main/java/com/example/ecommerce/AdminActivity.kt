package com.example.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class AdminActivity : AppCompatActivity() {

    private lateinit var CategoryName:String
    private lateinit var InputProductImage:ImageView
    private lateinit var InputProductName:EditText
    private lateinit var InputProductDesc:EditText
    private lateinit var InputProductPrice:EditText
    private lateinit var AddProductButton:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Toast.makeText(this, "Welcome to Admin Panel", Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show()
        CategoryName = getIntent().getExtras()?.get("category").toString()

        //Initialize Views
        InputProductImage = findViewById(R.id.select_product)
        InputProductName = findViewById(R.id.product_name)
        InputProductDesc =  findViewById(R.id.product_desc)
        InputProductPrice = findViewById(R.id.product_price)
        AddProductButton =  findViewById(R.id.add_new_product)

    }
}
