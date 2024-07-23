package com.example.jobs

import android.app.Application

class JobsApp : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}


