package com.katherinekurokawa.definitiveprojecy

import android.app.Application
import androidx.room.Room

class MyApplicaction : Application() {
    //INCIAMOS LA DB
    lateinit var room : MyDataBase
    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "MyDataBase"
        ).fallbackToDestructiveMigration().build()
    }
}