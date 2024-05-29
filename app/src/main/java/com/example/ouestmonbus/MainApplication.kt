package com.diplay.ouestmonbus

import android.app.Application
import androidx.room.Room
import model.AppDatabase

class MainApplication: Application() {
    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        )/*.fallbackToDestructiveMigration()*/.build()
    }
}