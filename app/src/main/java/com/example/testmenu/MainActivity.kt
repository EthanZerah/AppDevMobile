package com.example.testmenu

import android.content.Context
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val sharedPrefFile = "testsharedpref"

    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var sharedPoints = sharedPreferences.getInt("Points",0)
        var sharedBonus = sharedPreferences.getInt("Bonus",1)

        val outputCompt = findViewById<TextView>(R.id.text_compt)
        outputCompt.setText("Points : ${sharedPoints.toString()}     Bonus : ${sharedBonus.toString()}")
        val add: FloatingActionButton = findViewById(R.id.add)
        add.setOnClickListener {
            var sharedPoints = sharedPreferences.getInt("Points",0)
            var sharedBonus = sharedPreferences.getInt("Bonus",1)
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putInt("Points",sharedPoints+sharedBonus)
            editor.apply()
            outputCompt.setText("Points : ${sharedPoints.toString()}     Bonus : ${sharedBonus.toString()}")
        }
        val btnBonus = findViewById<Button>(R.id.bonus)
        btnBonus.setOnClickListener(View.OnClickListener {
            var sharedPoints = sharedPreferences.getInt("Points",0)
            var sharedBonus = sharedPreferences.getInt("Bonus",1)
            if (sharedPoints>=50) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("Bonus", sharedBonus + 1)
                editor.putInt("Points", sharedPoints - 50)
                editor.apply()
            }
            else {
                Snackbar.make(it, "Il faut minimum 50 points pour un nouveau bonus", Snackbar.LENGTH_LONG).show()
            }
            outputCompt.setText("Points : ${sharedPoints.toString()}     Bonus : ${sharedBonus.toString()}")
        })
        val btnReset = findViewById<Button>(R.id.reset)
        btnReset.setOnClickListener(View.OnClickListener {
            var sharedPoints = sharedPreferences.getInt("Points",0)
            var sharedBonus = sharedPreferences.getInt("Bonus",1)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("Bonus", 1)
            editor.putInt("Points", 0)
            editor.apply()
            outputCompt.setText("Points : ${sharedPoints.toString()}     Bonus : ${sharedBonus.toString()}")
            Snackbar.make(it, "Bien remis à zéro", Snackbar.LENGTH_LONG).show()
        })

        val outputUser = findViewById<TextView>(R.id.text_users)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retrofit.create(UserApi::class.java)
        val userRequest = service.getUser()
        userRequest.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val allUser = response.body()
                outputUser.setText("${allUser.toString()}")
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                error("ça marche pas !")
            }
        })

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

