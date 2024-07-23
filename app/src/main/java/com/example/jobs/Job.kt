package com.example.jobs

data class JobsDataResponse(
    val results: List<Job>
)


data class Job (
    val id : Long,
    val title: String,
    val whatsapp_no: Long,
    val salary_max: Int,
    val salary_min: Int,
    val company_name: String,
    val job_hours: String,
    val job_role: String,
    val job_category: String,
    val other_details: String,
    val num_applicantions: Int,
    val primary_details: primary_details?

)

data class primary_details (
    val Place: String?
)