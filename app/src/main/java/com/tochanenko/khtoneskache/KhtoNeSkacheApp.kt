package com.tochanenko.khtoneskache

import android.app.Application
import com.tochanenko.khtoneskache.database.AppDatabase

class KhtoNeSkacheApp: Application() {
    val db by lazy {
        AppDatabase.getInstance(this)
    }
}