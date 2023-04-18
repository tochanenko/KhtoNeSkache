package com.tochanenko.khtoneskache

import android.app.Application

class KhtoNeSkacheApp: Application() {
    val db by lazy {
        AppDatabase.getInstance(this)
    }
}