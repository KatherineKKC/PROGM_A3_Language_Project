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

    @Query("SELECT COUNT(*) > 0 FROM language WHERE name = :nameLanguageParameter")
    suspend fun languageExist(nameLanguageParameter: String): Boolean


    @Query("SELECT * FROM language WHERE name = :nameLanguageParameter")
    suspend fun getLanguage(nameLanguageParameter: String): Language


    @Query("SELECT * FROM language WHERE name = :idLanguageParameter")
    suspend fun getLanguageWithId(idLanguageParameter: Int): Language


    @Query("DELETE  FROM language ")
    suspend fun delleteAllLanguages()


    @Update
    suspend fun updateName(newLanguage : Language)

    @Delete
    suspend fun deleteLanguage(languageRemove :Language)
}