package com.katherinekurokawa.definitiveprojecy.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.katherinekurokawa.definitiveprojecy.entities.Project
import com.katherinekurokawa.definitiveprojecy.entities.ProjectWithLanguage

@Dao
interface ProjectDao {

    @Insert
    suspend fun addProject(project: Project)

    //obtenemos la relacion de proyecto lenguaje
    @Transaction
    @Query("SELECT * FROM project")
    suspend fun getProjectsWithLanguages(): List<ProjectWithLanguage>


}
