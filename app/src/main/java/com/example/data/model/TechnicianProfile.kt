package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "technician_profile")
data class TechnicianProfile(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "Manoj Prasanna",
    val role: String = "Chief Sewing Line Technician",
    val factoryName: String = "Apparel Tech Centre - Colombo",
    val experienceYears: Int = 8,
    val membershipLevel: String = "CERTIFIED", // APPRENTICE, CERTIFIED, MASTER
    val quizScore: Int = 85,
    val completedPmCount: Int = 12
)
