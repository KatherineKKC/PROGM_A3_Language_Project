package com.katherinekurokawa.definitiveprojecy.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.katherinekurokawa.definitiveprojecy.entities.Language

@Dao
interface LanguageDao {

    // Insertar un lenguaje
    @Insert
    suspend fun addLanguage(language: Language)

    // Obtener todos los lenguajes
    @Query("SELECT * FROM language")
    suspend fun getAllLanguages(): List<Language>
}