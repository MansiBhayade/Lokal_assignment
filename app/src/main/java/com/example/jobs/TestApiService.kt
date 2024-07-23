package com.example.jobs

import retrofit2.Call
import retrofit2.http.GET

interface TestApiService {

    @GET("jobs?page=1")
    fun getJobs(): Call<JobsDataResponse>


}