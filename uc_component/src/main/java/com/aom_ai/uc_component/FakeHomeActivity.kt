package com.aom_ai.uc_component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aom_ai.uc_component.databinding.ActivityFakeHomeBinding

class FakeHomeActivity : AppCompatActivity() {

    private var binding: ActivityFakeHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_home)

        binding = ActivityFakeHomeBinding.inflate(layoutInflater)
        binding?.bottomNavigation?.setOnNavigationItemSelectedListener { item ->
            return@setOnNavigationItemSelectedListener when (item.itemId) {
                R.id.llp_nav_my_courses -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.llp_nav_browse -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.llp_nav_search -> {
                    // Respond to navigation item 1 click
                    true
                }
                else  -> false
            }
        }
        binding?.bottomNavigation?.selectedItemId = R.id.llp_nav_my_courses
    }
}