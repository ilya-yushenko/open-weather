package android.yushenko.openweather.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.yushenko.openweather.R
import android.yushenko.openweather.ui.main.MainFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        // supportFragmentManager.beginTransaction().add(R.id.container_fragment, MainFragment.newInstance()).commit()
    }
}