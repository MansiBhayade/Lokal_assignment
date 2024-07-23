package com.example.jobs

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity (){

    private lateinit var title: TextView
    private lateinit var place: TextView
    private lateinit var company_name: TextView
    private lateinit var whatsapp_no: TextView
    private lateinit var job_hours: TextView
    private lateinit var job_role: TextView
    private lateinit var job_category: TextView
    private lateinit var no_applicants: TextView
    private lateinit var job_desc: TextView
    private val jobsList = mutableListOf<Job>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        title = findViewById(R.id.title)
        place = findViewById(R.id.place)
        company_name = findViewById(R.id.company_name)
        whatsapp_no = findViewById(R.id.whatsapp_no)
       job_role = findViewById(R.id.job_role)
        job_hours = findViewById(R.id.job_hours)
        job_category = findViewById(R.id.job_category)
        no_applicants = findViewById(R.id.no_applicants)
        job_desc = findViewById(R.id.job_desc)



        val jobId = intent.getLongExtra("JOB_ID", -1)
//       title.text = jobId.toString()
        fetchjobdetails(jobId)




    }

    private fun fetchjobdetails(jobId: Long){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/common/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(TestApiService::class.java)

        api.getJobs().enqueue(object : Callback<JobsDataResponse> {
            override fun onResponse(call: Call<JobsDataResponse>, response: Response<JobsDataResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                            jobsDataResponse ->
                        val job = jobsDataResponse.results.find { it.id == jobId }
                        Log.d("DetailsActivity", "Job found: $job")
                        displayJobDetails(job)

                    }
                }
            }

            override fun onFailure(call: Call<JobsDataResponse>, t: Throwable) {
                Toast.makeText(this@DetailsActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayJobDetails(job: Job?) {
        if (job != null) {
            title.text = job.title
            place.text = "Place: ${job.primary_details?.Place ?: "Location not available"}"
            company_name.text = "Company Name: ${job.company_name ?: "N/A"}"
            whatsapp_no.text = "Contact: ${job.whatsapp_no.toString()}"
            job_role.text = "Job Role: ${job.job_role ?: "N/A"}"
            job_hours.text = "Job Hours: ${job.job_hours ?: "N/A"}"
            job_category.text = "Job Category: ${job.job_category ?: "N/A"}"
            no_applicants.text = "Number of Applications: ${job.num_applicantions?.toString() ?: "N/A"}"
            job_desc.text = job.other_details ?: "N/A"
        }else {
            Toast.makeText(this, "Job details not available", Toast.LENGTH_SHORT).show()
        }
    }
}