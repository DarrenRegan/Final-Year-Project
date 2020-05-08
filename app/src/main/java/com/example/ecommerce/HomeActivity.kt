package com.example.ecommerce

import android.os.Bundle
import android.view.LayoutInflater
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
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.Model.Products
import com.example.ecommerce.Model.Products.Companion.description
import com.example.ecommerce.Model.Products.Companion.image
import com.example.ecommerce.Model.Products.Companion.pname
import com.example.ecommerce.Model.Products.Companion.price
import com.example.ecommerce.Prevalent.Prevalent
import com.example.ecommerce.views.ProductView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.paperdb.Paper

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var productsRef:DatabaseReference
    private lateinit var recyclerView:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        productsRef = FirebaseDatabase.getInstance().reference.child("Products")
        Paper.init(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("Home")
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
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
        var headerView = navView.getHeaderView(0)
        var userNameTextView:TextView = headerView.findViewById(R.id.user_profile_name)
        var profileImageView:CircleImageView = headerView.findViewById(R.id.user_profile_image)

        userNameTextView.text = Prevalent.currentOnlineUser.getName()

        recyclerView = findViewById(R.id.recycler_menu)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }//onCreate


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()

        var options = FirebaseRecyclerOptions.Builder<Products>().setQuery(productsRef, Products::class.java).build()

        var adapter:FirebaseRecyclerAdapter<Products, ProductView> = object:FirebaseRecyclerAdapter<Products, ProductView>(options) {
            override fun onBindViewHolder(holder: ProductView, position: Int, model: Products) {
                holder.txtProdName.text = pname
                holder.txtProdDesc.text = description
                holder.txtProdPrice.text = "Price: ${price}$"
                Picasso.get().load(image).into(holder.txtImageView)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductView {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.products_layout, parent, false)
                return ProductView(view)
            }
        }//adapter
        recyclerView.adapter = adapter
        adapter.startListening()
    }//onStart

} //HomeActivity

/*        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_send -> {//LOGOUT
                    Paper.book().destroy()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    finish()
                    startActivity(intent)
                }
            }//when
            false
        }*/