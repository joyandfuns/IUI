package com.aom_ai.learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aom_ai.learning.databinding.ActivityLlpLearningBinding

class LearningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLlpLearningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLlpLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}