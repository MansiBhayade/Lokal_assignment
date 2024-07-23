package com.example.jobs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var errorText: View
    private lateinit var emptyText: View
    private val jobsList = mutableListOf<Job>()
    private lateinit var jobsAdapter: JobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        errorText = view.findViewById(R.id.error_text)
        emptyText = view.findViewById(R.id.empty_text)

        recyclerView.layoutManager = LinearLayoutManager(context)
        jobsAdapter = JobsAdapter(jobsList)
        recyclerView.adapter = jobsAdapter

        Log.d("HomeFragment", "RecyclerView and Adapter set")

        fetchjobs()

        return view
    }

    private fun fetchjobs(){
        showLoading()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/common/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(TestApiService::class.java)

        api.getJobs().enqueue(object : Callback<JobsDataResponse> {
            override fun onResponse(call: Call<JobsDataResponse>, response: Response<JobsDataResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("HomeFragment", "Response received: ${it.results}")
                        jobsList.clear()
                        jobsList.addAll(it.results)
                        Log.d("HomeFragment", "Jobs List: $jobsList")
                        jobsAdapter.notifyDataSetChanged()
                        if (jobsList.isEmpty()) {
                            showEmptyState()
                        } else {
                            showContent()
                        }
                    } ?: showEmptyState()
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<JobsDataResponse>, t: Throwable) {
                Log.e("HomeFragment", "Failed to fetch jobs", t)
                showError()
            }
        })
    }

    private fun showLoading(){
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorText.visibility = View.GONE
        emptyText.visibility = View.GONE
    }

    private fun showError() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorText.visibility = View.VISIBLE
        emptyText.visibility = View.GONE
    }

    private fun showEmptyState() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorText.visibility = View.GONE
        emptyText.visibility = View.VISIBLE
    }

    private fun showContent() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        emptyText.visibility = View.GONE
    }
}
