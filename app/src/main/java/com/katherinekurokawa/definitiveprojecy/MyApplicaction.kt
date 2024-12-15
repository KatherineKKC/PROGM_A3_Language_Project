package com.katherinekurokawa.definitiveprojecy

import android.app.Application
import androidx.room.Room

class MyApplicaction : Application() {
    lateinit var room : MyDataBase //Instanciamos la DB
    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "MyDataBase"
        ).fallbackToDestructiveMigration().build()
    }
}