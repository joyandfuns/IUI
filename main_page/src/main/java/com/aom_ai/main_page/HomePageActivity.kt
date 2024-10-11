package com.aom_ai.main_page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aom_ai.main_page.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {

    private var binding: ActivityHomePageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        binding?.bottomNavigation?.setOnNavigationItemSelectedListener { item ->
//            return@setOnNavigationItemSelectedListener when (item.itemId) {
//                R.id.llp_nav_my_courses -> {
//                    // Respond to navigation item 1 click
//                    true
//                }
//                R.id.llp_nav_browse -> {
//                    // Respond to navigation item 1 click
//                    true
//                }
//                R.id.llp_nav_search -> {
//                    // Respond to navigation item 1 click
//                    true
//                }
//                else  -> false
//            }
//        }
//        binding?.bottomNavigation?.selectedItemId = R.id.llp_nav_my_courses
    }
}