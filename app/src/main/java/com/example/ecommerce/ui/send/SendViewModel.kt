package com.example.ecommerce.ui.send

import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.LoginActivity
import com.example.ecommerce.R
import io.paperdb.Paper

class SendViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = " "
    }
    val text: LiveData<String> = _text


}

/*
val logout: Button = root.findViewById(R.id.logout_btn)
logout.setOnClickListener { view ->
    Paper.book().destroy()

    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)

    Toast.makeText(this, "Logout Successfully! ", Toast.LENGTH_SHORT).show()
}*/