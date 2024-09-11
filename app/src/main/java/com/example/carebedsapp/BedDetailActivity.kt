package com.example.carebedsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carebedsapp.databinding.ActivityBedDetailBinding

class BedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBedDetailBinding
    private lateinit var bed: Bed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the bed from intent
        val bed: Bed? = intent.getSerializableExtra("bed") as? Bed

        if (bed != null) {
            // Use the bed data
            binding.bedId.text = bed.id.toString()
            binding.hospitalName.text = bed.hospitalName

            binding.bedStatus.text = bed.status
        } else {
            // Handle the case where bed is null (e.g., show an error message or finish the activity)
            Toast.makeText(this, "Bed data not available", Toast.LENGTH_SHORT).show()

            finish() // Optionally close the activity if the data is critical
        }
    }
}