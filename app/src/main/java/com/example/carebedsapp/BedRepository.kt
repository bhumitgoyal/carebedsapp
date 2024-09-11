package com.example.carebedsapp
// BedRepository.kt

object BedRepository {
    val beds = mutableListOf(
        Bed(1, "City Hospital", "New York", "Available"),
        Bed(2, "General Hospital", "Los Angeles", "Available"),
        Bed(3, "Saint Mary's", "San Francisco", "Occupied")
    )
}
