package com.example.carebedsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carebedsapp.databinding.ActivityWaitingRoomBinding

class WaitingRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaitingRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitingRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val patient = getPatientFromIntent() // Implement this method
        if (patient != null) {
            binding.tvWaitingMessage.text = "Your Token: ${patient.token}"
            updateWaitingTime(patient)

            binding.btnRefresh.setOnClickListener {
                updateWaitingTime(patient)
            }
        } else {
            // Handle the case where patient is null (e.g., show an error message)
            binding.tvWaitingMessage.text = "Patient information is not available."
        }
    }

    private fun getPatientFromIntent(): Patient? {
        return intent.getSerializableExtra("patient") as? Patient
    }

    private fun updateWaitingTime(patient: Patient) {
        val remainingTime = patient.waitingTime?.let { calculateRemainingTime(it) } ?: "N/A"
        binding.tvWaitingTime.text = "Estimated Waiting Time: $remainingTime"
    }

    private fun calculateRemainingTime(waitingTime: Long): String {
        // Convert waitingTime (in milliseconds) to a readable format
        val minutes = (waitingTime / 60000).toInt()
        val seconds = ((waitingTime % 60000) / 1000).toInt()
        return String.format("%02d:%02d", minutes, seconds)
    }
}
