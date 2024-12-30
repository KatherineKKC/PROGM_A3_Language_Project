package com.katherinekurokawa.definitiveprojecy.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "project")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val idProject: Int,
    val nameProject: String,
    val description: String,
    var priority: String,
    val data: String,
    var hours: String,
    var languageId: Int,
    var detailProject: String

)

data class ProjectWithLanguage(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "languageId",
        entityColumn = "idLanguage"
    )
    val language: Language?
)