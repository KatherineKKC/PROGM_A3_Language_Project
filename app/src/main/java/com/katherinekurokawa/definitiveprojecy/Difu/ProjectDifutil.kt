package com.katherinekurokawa.definitiveprojecy.Difu

import androidx.recyclerview.widget.DiffUtil
import com.katherinekurokawa.definitiveprojecy.entities.Project
import com.katherinekurokawa.definitiveprojecy.entities.ProjectWithLanguage

class ProjectDifutil(
    private val oldList :List<ProjectWithLanguage>,
    private val newList : List<ProjectWithLanguage>
) : DiffUtil.Callback( ){
    override fun getOldListSize(): Int {
        return  oldList.size
    }

    override fun getNewListSize(): Int {
         return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

     return   oldList[oldItemPosition].project.idProject == newList[newItemPosition].project.idProject
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return   oldList[oldItemPosition] == newList[newItemPosition]
    }
}