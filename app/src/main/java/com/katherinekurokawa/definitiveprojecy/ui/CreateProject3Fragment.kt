package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateProject3Binding
import com.katherinekurokawa.definitiveprojecy.entities.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.katherinekurokawa.definitiveprojecy.R


class CreateProject3Fragment : Fragment() {
    private lateinit var _binding: FragmentCreateProject3Binding
    private val binding : FragmentCreateProject3Binding get() = _binding
    private lateinit var  application : MyApplicaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProject3Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnCreateProject.setOnClickListener{
            getDatesOfFragments()
            val nameProject = arguments?.getString("nameProject").orEmpty()
            val description = arguments?.getString("description").orEmpty()
            val priority = arguments?.getString("priority").orEmpty()
            val date = arguments?.getString("date").orEmpty()
            val durationParse = arguments?.getString("duration").orEmpty()
            val languageId= arguments?.getInt("languageId") ?:0
            val message = arguments?.getString("message").orEmpty()

            val detailProject = binding.etDetailPro.text.toString()
            val duration = durationParse.toDoubleOrNull() ?: 0.0

            if(!message.isNullOrBlank() || detailProject.isNullOrBlank()){
                Toast.makeText(requireContext(), "Los datos estan incompletos ", Toast.LENGTH_SHORT).show()
            }else{
                 createProject(nameProject,description,priority,date,duration,languageId,detailProject)
            }
        }

    }

    //Funcion para pedir datos
    private fun getDatesOfFragments(){
        val fragment1 = CreateProject1Fragment()
        val fragment2 = CreateProject2Fragment()
        val args = Bundle()
        val  getData = true
        args.putBoolean("dataProject", getData)
        fragment1.arguments = args
        fragment2.arguments = args
    }

    private fun createProject(
        nameProject: String, description: String, priority: String, date: String,
        duration: Double, languageId: Int, detailProject: String
    )
    {
        lifecycleScope.launch(Dispatchers.IO) {
            val id=0
            try {
                val app =  application as  MyApplicaction
                val projectNew = app.room.projectDao().addProject(
                    Project(
                        idProject = id,
                        nameProject = nameProject,
                        description = description,
                        priority = priority,
                        data = date,
                        hours = duration,
                        languageId = languageId,
                        detailProject = detailProject)
                )
                Toast.makeText(requireContext(), "Proyecto creado", Toast.LENGTH_SHORT).show()
            }catch(e: Exception){
                Toast.makeText(requireContext(), "Error al crear el project", Toast.LENGTH_SHORT).show()
            }
        }



    }
}