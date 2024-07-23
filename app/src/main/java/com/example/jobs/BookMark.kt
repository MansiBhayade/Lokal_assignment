package com.example.jobs


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookMark")
data class BookMark(
    @PrimaryKey val id: Long,
    val title: String,
    val whatsappNo: Long,
    val salaryMax: Int,
    val salaryMin: Int,
    val place: String?
)
