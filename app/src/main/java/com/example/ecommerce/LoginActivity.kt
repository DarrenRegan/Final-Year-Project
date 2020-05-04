package com.example.ecommerce

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.CheckBox
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ecommerce.Prevalent.Prevalent
import com.example.ecommerce.Model.Users
import com.firebase.ui.auth.data.model.User
import com.google.firebase.database.*
import io.paperdb.Paper

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private lateinit var InputPassword: EditText
    private lateinit var InputPhoneNumber: EditText
    private lateinit var LoginButton: Button
    private lateinit var loadingBar: ProgressDialog
    private lateinit var checkBoxRememberMe: CheckBox
    private val parentDbName = "Users"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginButton = findViewById(R.id.login_btn)
        InputPhoneNumber = findViewById(R.id.login_phone_number_input)
        InputPassword = findViewById(R.id.login_password_input)
        loadingBar = ProgressDialog(this)

        checkBoxRememberMe = findViewById(R.id.remember_me_chk)
        Paper.init(this)


        LoginButton.setOnClickListener{
            LoginUser()
        }
    }//onCreate

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

        if (checkBoxRememberMe.isChecked){
            Paper.book().write(Prevalent.UserPhoneKey, phone)
            Paper.book().write(Prevalent.UserPasswordKey, password)
        }

        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()

        RootRef.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //var userData:Users
                if(dataSnapshot.child(parentDbName).child(phone).exists()){
                    var usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users::class.java)

                    if (usersData?.getPhone().equals(phone)){
                        if (usersData?.getPassword().equals(password)){
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            loadingBar.dismiss()
                            //Sends user to HomeActivity
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "Password is Incorrect...", Toast.LENGTH_SHORT).show()
                            loadingBar.dismiss()
                        }
                    }//getPhone if


                }
                else{
                    Toast.makeText(this@LoginActivity, "Account with this " + phone + " Number does not exist", Toast.LENGTH_SHORT).show()
                    loadingBar.dismiss()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            })

    }
}
