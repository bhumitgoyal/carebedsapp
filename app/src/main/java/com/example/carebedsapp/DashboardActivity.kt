package com.example.carebedsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carebedsapp.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var patientAdapter: PatientAdapter
    private lateinit var bedAdapter: BedAdapter
    private var showOnlyAvailableBeds = false
    private lateinit var patientRepository: PatientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize patientRepository with the shared instance
        patientRepository = (application as MyApp).patientRepository

        // Add some dummy patients
        populateDummyPatients()

        // Set up RecyclerViews for patients and beds
        setupPatientRecyclerView()
        setupBedRecyclerView()

        // Set up button click listeners
        setupButtons()

        // Update statistics and progress bar
        updateBedStatistics()

        // Set up search filter
        setupSearchFilter()
    }

    private fun setupButtons() {
        binding.myProfileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivityForResult(intent, ADD_PATIENT_REQUEST_CODE)
        }

        binding.btnSortPatients.setOnClickListener {
            sortPatientsByPriority()
        }

        binding.btnFilterBeds.setOnClickListener {
            showOnlyAvailableBeds = !showOnlyAvailableBeds
            updateRecyclerView()
            binding.btnFilterBeds.text = if (showOnlyAvailableBeds) "Show All Beds" else "Show Available Beds"
        }
    }

    private fun setupSearchFilter() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterResults(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun sortPatientsByPriority() {
        val sortedPatients = patientRepository.getAllPatients().sortedBy { it.priority_num }
        patientAdapter.updateData(sortedPatients)
    }

    private fun populateDummyPatients() {
        patientRepository.addPatient(Patient(1, "John Doe", 30, "Male", "john.doe@gmail.com", "1234567890", "Critical", "High",0, "123 Main St"))
        patientRepository.addPatient(Patient(2, "Jane Smith", 25, "Female", "jane.smith@gmail.com", "0987654321", "Stable", "Medium",1, "456 Broadway"))
        patientRepository.addPatient(Patient(3, "Alan Turing", 70, "Female", "alan.turing@hotmail.com", "9818646823", "Stable", "Low", 2,"Vellore Road"))
    }

    private fun setupPatientRecyclerView() {
        val patients = patientRepository.getAllPatients()
        patientAdapter = PatientAdapter(patients) { patient ->
            val intent = Intent(this, PatientDetailActivity::class.java)
            intent.putExtra("patient", patient)
            startActivity(intent)
        }
        binding.patientRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.patientRecyclerView.adapter = patientAdapter
    }

    private fun setupBedRecyclerView() {
        val beds = BedRepository.beds
        bedAdapter = BedAdapter(beds) { bed ->
            val intent = Intent(this, BedDetailActivity::class.java)
            intent.putExtra("bed", bed)
            startActivity(intent)
        }
        binding.bedRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bedRecyclerView.adapter = bedAdapter
    }

    private fun updateRecyclerView() {
        val beds = BedRepository.beds
        val filteredBeds = if (showOnlyAvailableBeds) {
            beds.filter { it.status == "Available" }
        } else {
            beds
        }
        bedAdapter.updateData(filteredBeds) // Update adapter with filtered data
    }

    private fun updateBedStatistics() {
        val beds = BedRepository.beds
        val totalBeds = beds.size
        val availableBeds = beds.count { it.status == "Available" }
        val occupiedBeds = beds.count { it.status == "Occupied" }

        binding.tvTotalBeds.text = "Total Beds: $totalBeds"
        binding.tvAvailableBeds.text = "Available Beds: $availableBeds"
        binding.tvOccupiedBeds.text = "Occupied Beds: $occupiedBeds"

        val occupancyPercentage = if (totalBeds > 0) {
            (occupiedBeds.toDouble() / totalBeds.toDouble()) * 100
        } else {
            0.0
        }
        binding.progressBarBeds.progress = occupancyPercentage.toInt()
    }

    private fun filterResults(query: String) {
        val patients = patientRepository.getAllPatients()
        val filteredPatients = patients.filter { patient -> patient.name.contains(query, true) }
        patientAdapter.updateData(filteredPatients) // Update adapter with filtered data
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PATIENT_REQUEST_CODE && resultCode == RESULT_OK) {
            setupPatientRecyclerView() // Refresh the patient list
        }
    }

    companion object {
        private const val ADD_PATIENT_REQUEST_CODE = 1
    }
}
