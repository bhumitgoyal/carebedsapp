package com.example.carebedsapp

import android.app.Application

class MyApp : Application() {
    val patientRepository: PatientRepository by lazy { InMemoryPatientRepository() }
}
