package com.example.ecommerce

import android.app.Activity
import android.app.ProgressDialog
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
import androidx.annotation.NonNull
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

@Suppress("DEPRECATION")
class AdminActivity : AppCompatActivity() {

    private lateinit var CategoryName:String
    private lateinit var Desc:String
    private lateinit var Price:String
    private lateinit var Prod_Name:String
    private lateinit var saveCurrentDate:String
    private lateinit var saveCurrentTime:String
    private lateinit var productKey:String
    private lateinit var downloadImageUrl:String
    private lateinit var InputProductImage:ImageView
    private lateinit var InputProductName:EditText
    private lateinit var InputProductDesc:EditText
    private lateinit var InputProductPrice:EditText
    private lateinit var AddProductButton:Button
    private var ProductPick = 1
    private lateinit var ImageUri:Uri
    private lateinit var ProductImageRef:StorageReference
    private lateinit var ProductRef:DatabaseReference
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        CategoryName = getIntent().getExtras()?.get("category").toString()
        ProductImageRef = FirebaseStorage.getInstance().reference.child("Product Images")
        ProductRef = FirebaseDatabase.getInstance().reference.child("Products")

        //Initialize Views
        InputProductImage = findViewById(R.id.select_product)
        InputProductName = findViewById(R.id.product_name)
        InputProductDesc =  findViewById(R.id.product_desc)
        InputProductPrice = findViewById(R.id.product_price)
        AddProductButton =  findViewById(R.id.add_new_product)
        loadingBar = ProgressDialog(this)

        //OnClickListeners
        InputProductImage.setOnClickListener{
            openProducts()
        }

        AddProductButton.setOnClickListener{
            validateProducts()
        }


    }

    private fun validateProducts() {
        Desc = InputProductDesc.getText().toString()
        Price = InputProductPrice.getText().toString()
        Prod_Name = InputProductName.getText().toString()

        if(TextUtils.isEmpty(Desc)){
            Toast.makeText(this, "Please write a Product Desc ", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please write a Product Price ", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(Prod_Name)){
            Toast.makeText(this, "Please write a Product Name ", Toast.LENGTH_SHORT).show()
        }
        else{
            storeProductInfo()
        }
    }

    private fun storeProductInfo() {
        loadingBar.setTitle("Adding a Product.. ")
        loadingBar.setMessage("Please wait, while we add the product.. ")
        loadingBar.setCanceledOnTouchOutside(false)
        loadingBar.show()

        var c = Calendar.getInstance()

        var currentDate = SimpleDateFormat("MMM dd, yyyy")
        saveCurrentDate = currentDate.format(c.time)

        var currentTime = SimpleDateFormat("HH:mm:ss a")
        saveCurrentTime = currentTime.format(c.time)

        //Create a Random key
        productKey = saveCurrentDate + saveCurrentTime

        //Store Img in Firebase
        var filePath = ProductImageRef.child(ImageUri.lastPathSegment + productKey + ".jpg")
        val ut = filePath.putFile(ImageUri)

        val urlTask = ut.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            downloadImageUrl = filePath.downloadUrl.toString()
            return@continueWithTask filePath.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadImageUrl = task.result.toString()
                Toast.makeText(this@AdminActivity, "Product Image Uploaded ", Toast.LENGTH_SHORT).show()
                saveToDatabase()
            } else {
                loadingBar.dismiss()
                var ex = task.exception.toString()
                Toast.makeText(this@AdminActivity, "Error: $ex", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener {task ->
            var ex = task.cause.toString()
            Toast.makeText(this@AdminActivity, "Error: $ex", Toast.LENGTH_SHORT).show()
            loadingBar.dismiss()
        }
    }

    private fun saveToDatabase() {
        var pMap:HashMap<String, Any> = HashMap()
        pMap.put("pid", productKey)
        pMap.put("date", saveCurrentDate)
        pMap.put("time", saveCurrentTime)
        pMap.put("description", Desc)
        pMap.put("image", downloadImageUrl)
        pMap.put("category", CategoryName)
        pMap.put("price", Price)
        pMap.put("pname", Prod_Name)

        ProductRef.child(productKey).updateChildren(pMap).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                //Sends user to Admin Panel
                val intent = Intent(this@AdminActivity, AdminPanelActivity::class.java)
                startActivity(intent)

                loadingBar.dismiss()
                Toast.makeText(this@AdminActivity, "Product Added Successfully ", Toast.LENGTH_SHORT).show()
            }
            else{
                loadingBar.dismiss()
                var ex = task.exception.toString()
                Toast.makeText(this@AdminActivity, "Error: $ex", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun openProducts() {
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
