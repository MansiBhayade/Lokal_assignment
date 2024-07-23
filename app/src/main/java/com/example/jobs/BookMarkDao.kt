package com.example.jobs



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface BookMarkDao {

    @Insert
    suspend fun insertBookmark(bookmark: BookMark)

    @Delete
    suspend fun deleteBookmark(bookmark: BookMark)

    @Query("SELECT * FROM bookMark")
    suspend fun getAllBookmarks(): List<BookMark>

    @Query("SELECT EXISTS(SELECT 1 FROM BookMark WHERE id = :jobId)")
    suspend fun isBookmarked(jobId: Long): Boolean
}
