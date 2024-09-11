package com.example.carebedsapp

interface PatientRepository {
    fun getAllPatients(): List<Patient>
    fun getPatientById(id: Int): Patient?
    fun addPatient(patient: Patient)
    fun removePatient(id: Int)
    fun updatePatient(patient: Patient)
    fun setOnDataChangedListener(listener: () -> Unit)
    fun generateUniqueToken(): Int
    fun assignTokenToPatient(patientId: Int, token: Int, waitingTime: Long)
    fun getPatientWaitingTime(patientId: Int): Long?
}
