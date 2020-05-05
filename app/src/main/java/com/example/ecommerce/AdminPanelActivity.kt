package com.example.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}
