package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class AdminPanelActivity : AppCompatActivity() {

    //Layout 1 @activity_admin_panel.xml
    private lateinit var mensShirts:ImageView
    private lateinit var womansShirts:ImageView
    private lateinit var boysClothes:ImageView
    private lateinit var girlsClothes:ImageView
    //Layout 2 @activity_admin_panel.xml
    private lateinit var womansShoes:ImageView
    private lateinit var mensShoes:ImageView
    private lateinit var bedroomFurniture:ImageView
    private lateinit var furniture:ImageView
    //Layout 3 @activity_admin_panel.xml
    private lateinit var tablets:ImageView
    private lateinit var phones:ImageView
    private lateinit var books:ImageView
    private lateinit var gaming:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        //*******************************************************************
        //Layout 1 OnClickListeners - mensShirts, womansShirts, boysClothes, girlsClothes
        //*******************************************************************
        mensShirts.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Mens Shirts")
            startActivity(intent)
        }
        womansShirts.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Womans Shirts")
            startActivity(intent)
        }
        boysClothes.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Boys Clothes")
            startActivity(intent)
        }
        girlsClothes.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Girls Clothes")
            startActivity(intent)
        }

        //*******************************************************************
        //Layout 2 OnClickListeners - womansShoes, mensShoes, bedroomFurniture, furniture
        //*******************************************************************

        womansShoes.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Womans Shoes")
            startActivity(intent)
        }
        mensShoes.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Mens Shoes")
            startActivity(intent)
        }
        bedroomFurniture.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Bedroom Furniture")
            startActivity(intent)
        }
        furniture.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Furniture")
            startActivity(intent)
        }

        //*******************************************************************
        //Layout 3 OnClickListeners - tablets, phones, books, gaming
        //*******************************************************************

        tablets.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Tablets")
            startActivity(intent)
        }
        phones.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Phones")
            startActivity(intent)
        }
        books.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Books")
            startActivity(intent)
        }
        gaming.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("category", "Gaming")
            startActivity(intent)
        }

    }
}
