package com.example.ecommerce

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class AdminActivity : AppCompatActivity() {

    private lateinit var CategoryName:String
    private lateinit var Desc:String
    private lateinit var Price:String
    private lateinit var Prod_Name:String
    private lateinit var InputProductImage:ImageView
    private lateinit var InputProductName:EditText
    private lateinit var InputProductDesc:EditText
    private lateinit var InputProductPrice:EditText
    private lateinit var AddProductButton:Button
    private var ProductPick = 1
    private lateinit var ImageUri:Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        CategoryName = getIntent().getExtras()?.get("category").toString()

        //Initialize Views
        InputProductImage = findViewById(R.id.select_product)
        InputProductName = findViewById(R.id.product_name)
        InputProductDesc =  findViewById(R.id.product_desc)
        InputProductPrice = findViewById(R.id.product_price)
        AddProductButton =  findViewById(R.id.add_new_product)

        //OnClickListeners
        InputProductImage.setOnClickListener{
            OpenProducts()
        }

        AddProductButton.setOnClickListener{
            ValidateProducts()
        }


    }

    private fun ValidateProducts() {
        Desc = InputProductDesc.getText().toString()
        Price = InputProductPrice.getText().toString()
        Prod_Name = InputProductName.getText().toString()

        if(ImageUri == null){
            Toast.makeText(this, "Must add a product image!", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(Desc)){
            Toast.makeText(this, "Please write a Product Desc ", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please write a Product Price ", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(Prod_Name)){
            Toast.makeText(this, "Please write a Product Name ", Toast.LENGTH_SHORT).show()
        }
        else{
            StoreProductInfo()
        }
    }

    private fun StoreProductInfo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun OpenProducts() {
        var productIntent = Intent()
        productIntent.setAction(Intent.ACTION_GET_CONTENT)
        productIntent.setType("image/*")
        startActivityForResult(productIntent, ProductPick)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProductPick && resultCode == RESULT_OK && data!=null){
            ImageUri = data.getData()!!
            InputProductImage.setImageURI(ImageUri)
        }
    }
}
