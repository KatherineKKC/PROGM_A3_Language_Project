package com.katherinekurokawa.definitiveprojecy

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katherinekurokawa.definitiveprojecy.dao.LanguageDao
import com.katherinekurokawa.definitiveprojecy.dao.ProjectDao
import com.katherinekurokawa.definitiveprojecy.dao.UserDao
import com.katherinekurokawa.definitiveprojecy.entities.Language
import com.katherinekurokawa.definitiveprojecy.entities.Project
import com.katherinekurokawa.definitiveprojecy.entities.User

//HE TENIDO QUE AUMENTAR LAS VERSIONES YA QUE REALICE VARIOS CAMBIOS EN LAS ENTIDADES :(
@Database(entities = [Project::class, User::class, Language::class], version = 6)
abstract  class MyDataBase : RoomDatabase(){
    abstract fun projectDao(): ProjectDao
    abstract fun userDao(): UserDao
    abstract fun languageDao(): LanguageDao
}