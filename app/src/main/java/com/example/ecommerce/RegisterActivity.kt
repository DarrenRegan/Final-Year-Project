package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class RegisterActivity : AppCompatActivity() {

    private lateinit var CreateAccountButton: Button
    private lateinit var InputName: EditText
    private lateinit var InputPhoneNumber: EditText
    private lateinit var InputPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        CreateAccountButton = findViewById(R.id.register_btn)
        InputName = findViewById(R.id.register_user_name_input)
        InputPhoneNumber = findViewById(R.id.register_phone_number_input)
        InputPassword = findViewById(R.id.register_password_input)

    }
}
