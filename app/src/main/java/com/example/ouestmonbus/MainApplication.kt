package com.diplay.ouestmonbus

import android.app.Application
import androidx.room.Room
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import model.AppDatabase

class MainApplication: Application() {
    companion object {
        lateinit var appDatabase: AppDatabase
        lateinit var supabase: SupabaseClient
        lateinit var gtfsSupabase: SupabaseClient
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        )/*.fallbackToDestructiveMigration()*/.allowMainThreadQueries().build()
        supabase = createSupabaseClient(
            supabaseUrl = "https://xddvqmengnkoebmlehmc.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhkZHZxbWVuZ25rb2VibWxlaG1jIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUyOTE4ODgsImV4cCI6MjAzMDg2Nzg4OH0.fk8GO44O8BZcce0ic_q25vcfsTBWXw7BBGDClSD0FY0"
        ) {
            install(Postgrest)
        }
        gtfsSupabase = createSupabaseClient(
            supabaseUrl = "https://worldcrtwkdijutdjdcx.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndvcmxkY3J0d2tkaWp1dGRqZGN4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzIyNjM0MjYsImV4cCI6MjA0NzgzOTQyNn0.rNzKYD9h8ijJx6b8EvwQyw7yyeayJ98EFspFAEhDZMk"
        ) {
            install(Postgrest)
        }
    }
}