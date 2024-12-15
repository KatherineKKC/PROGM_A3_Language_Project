package com.katherinekurokawa.definitiveprojecy.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language")
data class Language(
    @PrimaryKey(autoGenerate = true) val idLanguage: Int,
    val name: String
)
