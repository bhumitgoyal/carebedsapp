package com.example.carebedsapp
class InMemoryPatientRepository : PatientRepository {

    private val patients = mutableListOf<Patient>()
    private var onDataChangedListener: (() -> Unit)? = null
    private var nextToken = 1 // A simple counter to generate unique tokens

    override fun getAllPatients(): List<Patient> {
        return patients
    }

    override fun getPatientById(id: Int): Patient? {
        return patients.find { it.id == id }
    }

    override fun addPatient(patient: Patient) {
        patients.add(patient)
        onDataChangedListener?.invoke()
    }

    override fun removePatient(id: Int) {
        patients.removeIf { it.id == id }
        onDataChangedListener?.invoke()
    }

    override fun updatePatient(patient: Patient) {
        val index = patients.indexOfFirst { it.id == patient.id }
        if (index != -1) {
            patients[index] = patient
            onDataChangedListener?.invoke()
        }
    }

    override fun setOnDataChangedListener(listener: () -> Unit) {
        onDataChangedListener = listener
    }

    override fun generateUniqueToken(): Int {
        return nextToken++
    }

    override fun assignTokenToPatient(patientId: Int, token: Int, waitingTime: Long) {
        val patient = getPatientById(patientId)
        patient?.let {
            it.token = token
            it.waitingTime = waitingTime
            updatePatient(it)
        }
    }

    override fun getPatientWaitingTime(patientId: Int): Long? {
        return getPatientById(patientId)?.waitingTime
    }
}
