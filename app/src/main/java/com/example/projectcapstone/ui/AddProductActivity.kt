package com.example.projectcapstone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectcapstone.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}