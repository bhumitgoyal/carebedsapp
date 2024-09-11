package com.example.carebedsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carebedsapp.databinding.ActivityPatientDetailBinding

class PatientDetailActivity : AppCompatActivity() {
    val beds = BedRepository.beds

    private lateinit var binding: ActivityPatientDetailBinding
    private lateinit var patient: Patient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the patient from intent
        patient = intent.getSerializableExtra("patient") as Patient

        // Display patient details
        binding.patientName.text = patient.name
        binding.patientAge.text = "Age: ${patient.age}"
        binding.patientCondition.text = "Condition: ${patient.condition}"
        binding.patientPriority.text = "Priority: ${patient.priority}"
        binding.patientAddress.text = "Address: ${patient.address}"

        // Alot Bed button click listener
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
    }
    private fun showAvailableBedsDialog() {

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


}