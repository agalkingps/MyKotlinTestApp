package ru.agalking.mykotlintestapp.views.loginflow

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.agalking.mykotlintestapp.R

class LoginActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sharedViewModel: LoginFlowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        deleteDatabase("database_name")
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val model: LoginFlowViewModel by viewModels()
        sharedViewModel = model
    }

}