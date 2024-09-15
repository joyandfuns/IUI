package com.aom_ai.uc_component

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.aom_ai.uc_component.databinding.ActivityAuthenticationBinding
import com.aom_ai.uc_component.utils.StatusBarCompat

class AuthenticationActivity : AppCompatActivity(), IAuthToolbarAction {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.setStatusBarStyle(this, Color.WHITE, true)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = ""

        val navController = findNavController(R.id.nav_host_fragment_content_login)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding?.llBack?.setOnClickListener {
            onBackPressed()
        }
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_login)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun hideBackIcon() {
        binding?.llBack?.visibility = View.GONE
    }

    override fun showBackIcon() {
        binding?.llBack?.visibility = View.VISIBLE
    }
}