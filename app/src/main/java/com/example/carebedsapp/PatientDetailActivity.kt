package com.example.carebedsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.carebedsapp.databinding.ActivityPatientDetailBinding
import java.util.concurrent.TimeUnit

class PatientDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientDetailBinding
    private lateinit var patient: Patient
    private lateinit var patientRepository: PatientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize patient repository
        patientRepository = InMemoryPatientRepository() // Replace with actual repository instance

        // Retrieve the patient from intent
        patient = intent.getSerializableExtra("patient") as Patient

        // Display patient details
        displayPatientDetails(patient)

        // Assign Bed button click listener
        val btnAlotBed: Button = binding.alotBedButton
        btnAlotBed.setOnClickListener {
            showAvailableBedsDialog()
        }

        // Call Patient button click listener
        binding.callPatientButton.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:${patient.phoneNo}")
            startActivity(dialIntent)
        }

        // Assign Token button click listener
        val btnAssignToken: Button = binding.assignTokenButton
        btnAssignToken.setOnClickListener {
            assignTokenToPatient()
        }
    }

    private fun displayPatientDetails(patient: Patient) {
        binding.patientName.text = patient.name
        binding.patientAge.text = "Age: ${patient.age}"
        binding.patientCondition.text = "Condition: ${patient.condition}"
        binding.patientPriority.text = "Priority: ${patient.priority}"
        binding.patientAddress.text = "Address: ${patient.address}"

        // Display token and waiting time if available
        patient.token?.let {
            binding.patientToken.text = "Token: $it"
        } ?: run {
            binding.patientToken.text = "Token: Not assigned"
        }

        patient.waitingTime?.let {
            val remainingTime = it - System.currentTimeMillis()
            if (remainingTime > 0) {
                binding.patientWaitingTime.text = "Waiting Time: ${TimeUnit.MILLISECONDS.toMinutes(remainingTime)} minutes"
            } else {
                binding.patientWaitingTime.text = "Bed Available"
            }
        } ?: run {
            binding.patientWaitingTime.text = "Waiting Time: Not assigned"
        }
    }

    private fun showAvailableBedsDialog() {
        val beds = BedRepository.beds
        val availableBeds = beds.filter { it.status == "Available" }.toTypedArray()

        if (availableBeds.isEmpty()) {
            Toast.makeText(this, "No available beds", Toast.LENGTH_SHORT).show()
            return
        }

        val bedNames = availableBeds.map { "Bed ${it.id} - ${it.hospitalName}" }.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Bed")
        builder.setItems(bedNames) { dialog, which ->
            val selectedBed = availableBeds[which]
            assignBedToPatient(selectedBed)
        }
        builder.show()
    }

    private fun assignBedToPatient(bed: Bed) {
        bed.status = "Occupied"
        patient.bed = bed
        Toast.makeText(this, "Bed ${bed.id} assigned to ${patient.name}", Toast.LENGTH_SHORT).show()
    }

    private fun assignTokenToPatient() {
        val token = patientRepository.generateUniqueToken()
        val waitingTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30) // Example: 30 minutes from now
        patientRepository.assignTokenToPatient(patient.id, token, waitingTime)
        patient.token = token
        patient.waitingTime = waitingTime
        displayPatientDetails(patient)
    }
}
