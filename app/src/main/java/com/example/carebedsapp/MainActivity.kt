package com.example.carebedsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carebedsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Using View Binding to set the correct layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example: Navigating to Login Page on app start
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // To close the current activity after navigating to LoginActivity
    }
}