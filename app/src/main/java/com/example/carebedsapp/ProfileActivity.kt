package com.example.carebedsapp

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.carebedsapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var patientRepository: PatientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize patientRepository with the shared instance
        patientRepository = (application as MyApp).patientRepository

        // Display dummy user profile
        binding.profileName.text = "John Admin"
        binding.profileEmail.text = "admin@example.com"

        // Add Patient
        binding.addPatientButton.setOnClickListener {
            showAddPatientDialog()
        }

        // Remove Patient
        binding.removePatientButton.setOnClickListener {
            // TODO: Implement remove patient functionality
        }

        // Add Bed
        binding.addBedButton.setOnClickListener {
            // TODO: Implement add bed functionality
        }

        // Remove Bed
        binding.removeBedButton.setOnClickListener {
            // TODO: Implement remove bed functionality
        }
    }

    private fun showAddPatientDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_patient, null)
        val etName = dialogView.findViewById<EditText>(R.id.etPatientName)
        val etAge = dialogView.findViewById<EditText>(R.id.etPatientAge)
        val etPhone = dialogView.findViewById<EditText>(R.id.etPatientPhone)

        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Add New Patient")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val name = etName.text.toString()
                val age = etAge.text.toString().toIntOrNull() ?: 0
                val phone = etPhone.text.toString()

                if (name.isNotEmpty() && age > 0 && phone.isNotEmpty()) {
                    addPatient(name, age, phone)
                } else {
                    // Handle invalid input (e.g., show a Toast message)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)

        dialogBuilder.create().show()
    }

    private fun addPatient(name: String, age: Int, phone: String) {
        // Generate a new patient ID
        val newId = (patientRepository.getAllPatients().maxOfOrNull { it.id } ?: 0) + 1
        val newPatient = Patient(newId, name, age, "unknown", phone, "unknown", "unknown", "unknown",1, "unknown")

        // Add the patient to the repository
        patientRepository.addPatient(newPatient)

        Log.d("ProfileActivity", "Patient added: $newPatient")

        // Notify DashboardActivity to refresh
        setResult(RESULT_OK)
        finish()
    }
}
