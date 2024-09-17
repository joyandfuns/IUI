package com.aom_ai.uc_component

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.aom_ai.uc_component.databinding.ActivityLlpAuthenticationBinding
import com.aom_ai.uc_component.utils.StatusBarCompat

class AuthenticationActivity : AppCompatActivity(), IAuthToolbarAction {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var binding: ActivityLlpAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.setStatusBarStyle(this, Color.WHITE, true)

        binding = ActivityLlpAuthenticationBinding.inflate(layoutInflater)
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

    override fun hideBack() {
        binding?.llBack?.visibility = View.GONE
    }

    override fun showBack() {
        binding?.llBack?.visibility = View.VISIBLE
    }

    override fun setTitle(title: String) {
        binding?.tvToolbarTitle?.text = title
    }
}