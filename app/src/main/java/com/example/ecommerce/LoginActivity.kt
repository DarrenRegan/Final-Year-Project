package com.example.ecommerce

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var InputPassword: EditText
    private lateinit var InputPhoneNumber: EditText
    private lateinit var LoginButton: Button
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginButton = findViewById(R.id.login_btn)
        InputPhoneNumber = findViewById(R.id.login_phone_number_input)
        InputPassword = findViewById(R.id.login_password_input)
        loadingBar = ProgressDialog(this)

        






    }
}
