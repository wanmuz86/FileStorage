package com.muzaffar.filestorage

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muzaffar.filestorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readInternalBtn.setOnClickListener {
            startActivity(Intent(this, ReadInternalActivity::class.java))
        }

        binding.readExternalBtn.setOnClickListener {
            startActivity(Intent(this,ReadExternalActivity::class.java))
        }

        binding.writeExternalBtn.setOnClickListener {
            startActivity(Intent(this, WriteExternalActivity::class.java))
        }

        binding.writeInternalBtn.setOnClickListener {
            startActivity(Intent(this,WriteInternalActivity::class.java))
        }

    }
}