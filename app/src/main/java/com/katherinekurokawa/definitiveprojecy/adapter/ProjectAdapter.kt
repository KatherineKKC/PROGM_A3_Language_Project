package com.katherinekurokawa.definitiveprojecy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.katherinekurokawa.definitiveprojecy.Difu.ProjectDifutil
import com.katherinekurokawa.definitiveprojecy.databinding.ItemProjectsBinding
import com.katherinekurokawa.definitiveprojecy.entities.Project
import com.katherinekurokawa.definitiveprojecy.entities.ProjectWithLanguage

class ProjectAdapter(var listProject :MutableList<ProjectWithLanguage>): RecyclerView.Adapter<ProjectAdapter.ProjectHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectsBinding.inflate(inflater, parent, false)
        return ProjectHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
         val project = listProject[position]
         holder.binding.tvNameProject.text =project.project.nameProject
        holder.binding.tvDurationProject.text =project.project.hours
        holder.binding.tvDateProject.text =project.project.data
        holder.binding.tvPriority.text =project.project.priority
        holder.binding.tvLanguageItem.text =project.project.languageId.toString()
    }

    override fun getItemCount(): Int {
        return listProject.size
    }

    fun submitList(newListProject: List<ProjectWithLanguage>){
        val difCalback = ProjectDifutil(listProject, newListProject)
        val difResult = DiffUtil.calculateDiff(difCalback)
        listProject.clear()
        listProject.addAll(newListProject)
        difResult.dispatchUpdatesTo(this)

    }

    inner class ProjectHolder(val binding : ItemProjectsBinding): RecyclerView.ViewHolder(binding.root){}
}