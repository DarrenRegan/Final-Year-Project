package com.example.ecommerce.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ecommerce.LoginActivity
import com.example.ecommerce.R
import io.paperdb.Paper

class LogoutFragment : Fragment() {

    private lateinit var logoutViewModel: LogoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logoutViewModel =
            ViewModelProviders.of(this).get(LogoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_logout, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        val logout: Button = root.findViewById(R.id.logout_btn)

        logoutViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        logout.setOnClickListener { view ->
            Paper.book().destroy()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return root
    }

}

