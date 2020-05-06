@file:Suppress("DEPRECATION")

package com.example.ecommerce

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Button
import android.view.View.OnClickListener;
import android.widget.Toast
import com.example.ecommerce.Model.Users
import com.example.ecommerce.Prevalent.Prevalent
import com.example.ecommerce.Prevalent.Prevalent.Companion.UserPhoneKey
import com.example.ecommerce.Prevalent.Prevalent.Companion.UserPasswordKey
import com.google.firebase.database.*
import io.paperdb.Paper

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var joinNowButton: Button
    private lateinit var loginButton: Button
    private lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        Paper.init(this)
        joinNowButton = findViewById(R.id.main_join_now_btn)
        loginButton = findViewById(R.id.main_login_btn)
        loadingBar = ProgressDialog(this)



        //, LoginActivity::class.java
        loginButton.setOnClickListener{view ->
            val intent = Intent(view.context, LoginActivity::class.java)
            view.context.startActivity(intent)
        }
        /*
        // UNCOMMENT TO TEST LoginActivity
        joinNowButton.setOnClickListener{view ->
            val intent = Intent(view.context, LoginActivity::class.java)
            view.context.startActivity(intent)
        } */

        //, LoginActivity::class.java
        joinNowButton.setOnClickListener{view ->
            val intent = Intent(view.context, RegisterActivity::class.java)
            view.context.startActivity(intent)
        }

        //Retrieve User Data for Remember me
        var userPhone = Paper.book().read<String>(UserPhoneKey)
        var userPass = Paper.book().read<String>(UserPasswordKey)

        if(userPhone != "" && userPass != ""){
            if(!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPass)){
                allowAccess(userPhone, userPass)
                loadingBar.setTitle("Already Logged In!")
                loadingBar.setMessage("Please wait... ")
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    } //onCreate end

    //Remember Me Allowing Access - https://github.com/pilgr/Paper
    private fun allowAccess(phone: String, password: String) {
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().getReference()

        RootRef.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //var userData:Users
                if(dataSnapshot.child("Users").child(phone).exists()){
                    var usersData = dataSnapshot.child("Users").child(phone).getValue(Users::class.java)

                    if (usersData?.getPhone().equals(phone)){
                        if (usersData?.getPassword().equals(password)){
                            Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            loadingBar.dismiss()
                            //Sends user to HomeActivity
                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Password is Incorrect...", Toast.LENGTH_SHORT).show()
                            loadingBar.dismiss()
                        }
                    }//getPhone if


                }
                else{
                    Toast.makeText(this@MainActivity, "Account with this " + phone + " Number does not exist", Toast.LENGTH_SHORT).show()
                    loadingBar.dismiss()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
