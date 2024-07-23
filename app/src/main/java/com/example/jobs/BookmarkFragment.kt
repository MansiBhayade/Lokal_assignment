package com.example.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookMarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewBookmarks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadBookmarks()

        return view
    }

    private fun loadBookmarks() {
        val dao = AppDatabase.getDatabase(requireContext()).bookmarkDao()

        // Launch a coroutine to perform the database operation on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            val bookmarks = dao.getAllBookmarks()
            withContext(Dispatchers.Main) {
                // Update the RecyclerView adapter on the main thread
                adapter = BookMarkAdapter(bookmarks)
                recyclerView.adapter = adapter
            }
        }
    }
}

