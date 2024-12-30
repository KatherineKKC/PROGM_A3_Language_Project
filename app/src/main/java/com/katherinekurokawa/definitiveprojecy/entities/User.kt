package com.katherinekurokawa.definitiveprojecy.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nameUser: String,
    val passwordUser: String
)


