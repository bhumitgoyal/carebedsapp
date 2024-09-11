package com.example.carebedsapp

import java.io.Serializable

data class Patient(
    val id: Int,
    val name: String,
    val age: Int,
    val gender: String,
    val email: String,
    val phoneNo: String,
    val condition: String,
    val priority: String,
    val address: String,
    var bed: Bed? = null // initially no bed assigned
) : Serializable