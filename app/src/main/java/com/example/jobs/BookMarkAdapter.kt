package com.example.jobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookMarkAdapter(private val bookmarks: List<BookMark>) :
    RecyclerView.Adapter<BookMarkAdapter.BookmarkViewHolder>() {

    class BookmarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.title)
        val placeTextView: TextView = view.findViewById(R.id.place)
        val salaryminTextView: TextView = view.findViewById(R.id.salary_min)
        val salarymaxTextView: TextView = view.findViewById(R.id.salary_max)
        val contactTextView: TextView = view.findViewById(R.id.contact_no)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.titleTextView.text = bookmark.title
        holder.placeTextView.text = bookmark.place
        holder.contactTextView.text= "Contact : ${bookmark.whatsappNo.toString()}"
        holder.salarymaxTextView.text = "₹${bookmark.salaryMax.toString()}"
        holder.salaryminTextView.text = "Salary: ₹${bookmark.salaryMin.toString()}"
    }

    override fun getItemCount() = bookmarks.size
}
