package com.example.jobs

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JobsAdapter(private val jobs: List<Job>) : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contact_no: TextView = itemView.findViewById(R.id.contact_no)
        val salary_min: TextView = itemView.findViewById(R.id.salary_min)
        val salary_max: TextView = itemView.findViewById(R.id.salary_max)
        val Place: TextView = itemView.findViewById(R.id.place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        Log.d("JobsAdapter", "onCreateViewHolder called")
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        Log.d("JobsAdapter", "Binding job: ${job.title}")
        holder.title.text = job.title
        holder.contact_no.text = "☎️ ${job.whatsapp_no}"
        holder.salary_min.text = "Salary: ₹${job.salary_min}"
        holder.salary_max.text = "₹${job.salary_max}"
         holder.Place.text = "Place : ${job.primary_details?.Place ?:"Location not available"}"
    }

    override fun getItemCount(): Int {
        Log.d("JobsAdapter", "getItemCount called: ${jobs.size}")
        return jobs.size
    }
}

