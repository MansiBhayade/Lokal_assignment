package com.example.jobs

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobsAdapter(private val context: Context,
                  private val jobs: List<Job>,
                  private val bookmarkDao: BookMarkDao) : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {


    private val bookmarkedJobs = mutableSetOf<Long>()

    init {
        loadBookmarkedJobs()
    }

    private fun loadBookmarkedJobs() {
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarks = bookmarkDao.getAllBookmarks()
            withContext(Dispatchers.Main) {
                bookmarkedJobs.addAll(bookmarks.map { it.id })
                notifyDataSetChanged()
            }
        }
    }

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val contact_no: TextView = itemView.findViewById(R.id.contact_no)
        val salary_min: TextView = itemView.findViewById(R.id.salary_min)
        val salary_max: TextView = itemView.findViewById(R.id.salary_max)
        val Place: TextView = itemView.findViewById(R.id.place)
        val bookmark: ImageView = itemView.findViewById(R.id.bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
//        Log.d("JobsAdapter", "onCreateViewHolder called")
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
//        Log.d("JobsAdapter", "Binding job: ${job.title}")
        holder.title.text = job.title
        holder.contact_no.text = "☎️ ${job.whatsapp_no}"
        holder.salary_min.text = "Salary: ₹${job.salary_min}"
        holder.salary_max.text = "₹${job.salary_max}"
         holder.Place.text = "Place : ${job.primary_details?.Place ?:"Location not available"}"


        holder.itemView.setOnClickListener{
            val intent = Intent(context,DetailsActivity::class.java ).apply {
                putExtra("JOB_ID", job.id)
            }
            context.startActivity(intent)
        }

        holder.bookmark.setImageResource(
            if (bookmarkedJobs.contains(job.id)) R.drawable.ic_bookmark
            else R.drawable.ic_bookmark_border
        )

        holder.bookmark.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (bookmarkedJobs.contains(job.id)) {
                    holder.bookmark.setImageResource(R.drawable.ic_bookmark_border)
                    bookmarkDao.deleteBookmark(
                        BookMark(
                            id = job.id,
                            title = job.title,
                            whatsappNo = job.whatsapp_no,
                            salaryMax = job.salary_max,
                            salaryMin = job.salary_min,
                            place = job.primary_details?.Place
                        )
                    )
                    bookmarkedJobs.remove(job.id)
                } else {
                    holder.bookmark.setImageResource(R.drawable.ic_bookmark)
                    bookmarkDao.insertBookmark(
                        BookMark(
                            id = job.id,
                            title = job.title,
                            whatsappNo = job.whatsapp_no,
                            salaryMax = job.salary_max,
                            salaryMin = job.salary_min,
                            place = job.primary_details?.Place
                        )
                    )
                    bookmarkedJobs.add(job.id)
                }
                withContext(Dispatchers.Main) {
                    notifyItemChanged(position)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return jobs.size
    }
}

