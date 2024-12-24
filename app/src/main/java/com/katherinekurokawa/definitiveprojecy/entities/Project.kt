package com.katherinekurokawa.definitiveprojecy.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "project")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val idProject: Int,
    val nameProject: String,
    val description: String,
    val priority: String,
    val data: String,
    val hours: String,
    val languageId: Int,
    val detailProject: String

)

data class ProjectWithLanguage(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "languageId",
        entityColumn = "idLanguage"
    )
    val language: Language?
)