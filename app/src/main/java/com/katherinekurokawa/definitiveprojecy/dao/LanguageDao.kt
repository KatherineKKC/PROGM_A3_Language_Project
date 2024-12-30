package com.katherinekurokawa.definitiveprojecy.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.katherinekurokawa.definitiveprojecy.entities.Language

@Dao
interface LanguageDao {

    @Insert
    suspend fun addLanguage(language: Language)

    @Query("SELECT * FROM language")
    suspend fun getAllLanguages(): List<Language>


    @Query("SELECT * FROM language WHERE name = :nameLanguageParameter")
    suspend fun getLanguage(nameLanguageParameter: String): Language

    @Query("SELECT name FROM language WHERE idLanguage = :idLanguageParameter")
    suspend fun getLanguageNameById(idLanguageParameter: Int): String

    @Query("DELETE  FROM language ")
    suspend fun deleteAllLanguages()

    @Update
    suspend fun updateLanguage(newLanguage : Language)

    @Delete
    suspend fun deleteLanguage(languageRemove :Language)
}