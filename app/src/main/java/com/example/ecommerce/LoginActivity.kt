package com.example.ecommerce

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*

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



        LoginButton.setOnClickListener{
            LoginUser()
        }




    }

    private fun LoginUser() {
        var phone = InputPhoneNumber.text.toString()
        var password = InputPassword.text.toString()

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Write your phone number... ", Toast.LENGTH_SHORT).show()
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Write your password... ", Toast.LENGTH_SHORT).show()
        }
        else{
            loadingBar.setTitle("Login Account")
            loadingBar.setMessage("Please wait, while we validate your information.")
            loadingBar.setCanceledOnTouchOutside(false)
            loadingBar.show()
            
            AllowAccessToAccount(phone, password)
        }
    }//LoginUser

    private fun AllowAccessToAccount(phone: String, password: String) {

        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()

        RootRef.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            })

    }
}
