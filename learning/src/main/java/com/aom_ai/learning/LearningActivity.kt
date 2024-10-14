package com.aom_ai.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aom_ai.learning.databinding.ActivityLlpLearningBinding
import com.aom_ai.uc_component.utils.StatusBarCompat

class LearningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLlpLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val statusBarColor = getColor(R.color.llp_background_1)
        StatusBarCompat.setStatusBarStyle(this, statusBarColor, true)

        binding = ActivityLlpLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}