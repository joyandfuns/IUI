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
    }
}