package com.example.ecommerce

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*


@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var CreateAccountButton: Button
    private lateinit var InputName: EditText
    private lateinit var InputPhoneNumber: EditText
    private lateinit var InputPassword: EditText
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        CreateAccountButton = findViewById(R.id.register_btn)
        InputName = findViewById(R.id.register_user_name_input)
        InputPhoneNumber = findViewById(R.id.register_phone_number_input)
        InputPassword = findViewById(R.id.register_password_input)
        loadingBar = ProgressDialog(this)

        CreateAccountButton.setOnClickListener{
            createAccount()
        }

    }

    private fun createAccount() {
        var name = InputName.text.toString()
        var phone = InputPhoneNumber.text.toString()
        var password = InputPassword.text.toString()

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Write your name... ", Toast.LENGTH_SHORT).show()
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Write your phone number... ", Toast.LENGTH_SHORT).show()
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Write your password... ", Toast.LENGTH_SHORT).show()
        }
        else{
            loadingBar.setTitle("Create Account")
            loadingBar.setMessage("Please wait, while we validate your information.")
            loadingBar.setCanceledOnTouchOutside(false)
            loadingBar.show()

            validatePhoneNumber(name, phone, password)

        }
    }
    //WORKS AFTER TESTING - SCHEMA USERS - KEY PHONENUMBER - DETAILS NAME:< PASSWORD:< PHONE:
    private fun validatePhoneNumber(name: String, phone: String, password: String) {

        val RootRef:DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()

        RootRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //val post = dataSnapshot.getValue(String::class.java)
                //Update the UI with received data
                if (!(dataSnapshot.child("Users").child(phone).exists())){
                    val childUpdates = HashMap<String, Any>()
                    childUpdates.put("phone", phone)
                    childUpdates.put("password", password)
                    childUpdates.put("name", name)

                    RootRef.child("Users").child(phone).updateChildren(childUpdates)
                        .addOnCompleteListener(object: OnCompleteListener<Void>{
                            override fun onComplete(@NonNull task: Task<Void>){
                                if (task.isSuccessful()){
                                    Toast.makeText(this@RegisterActivity, "Your Account has been created.", Toast.LENGTH_SHORT).show()
                                    loadingBar.dismiss()

                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)

                                }//if
                                else{
                                    loadingBar.dismiss()
                                    Toast.makeText(this@RegisterActivity, "Network Error: Please Try Again...", Toast.LENGTH_SHORT).show()
                                }
                            }//onComplete
                        })//addOnCompleteListener
                }//Data
                else{
                    Toast.makeText(this@RegisterActivity, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show()
                    loadingBar.dismiss()
                    Toast.makeText(this@RegisterActivity, "Please try another number", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }//onDataChange

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })//Listener

    }//end of method
}
