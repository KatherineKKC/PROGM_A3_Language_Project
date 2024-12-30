package com.katherinekurokawa.definitiveprojecy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateProject3Binding
import com.katherinekurokawa.definitiveprojecy.entities.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale


class CreateProject3Fragment : Fragment() {
    //--------------------------------------------VARIABLES------------------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentCreateProject3Binding
    private val binding: FragmentCreateProject3Binding get() = _binding

    //APLICACION
    private lateinit var application: MyApplicaction


    //--------------------------------------------VARIABLES------------------------------------------------//
    //INFLATER
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProject3Binding.inflate(inflater)
        return binding.root
    }

    //LOGICA FRAGMENT
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //INIALIZAR LA APLICACION
        application = requireActivity().application as MyApplicaction

        //ACCION BOTON PARA CREAR EL PROYECTO
        binding.btnCreateProject.setOnClickListener {
            //RECIBE LOS DATOS DE LOS ANTERIORES FRAGMENTS
            val nameProject = arguments?.getString("nameProject").orEmpty()
            val description = arguments?.getString("description").orEmpty()
            val priority = arguments?.getString("priority").orEmpty()
            val formateDate = arguments?.getString("date").orEmpty()
            val duration = arguments?.getString("duration").orEmpty()
            val languageId = arguments?.getInt("languageId") ?: 0
            val detailProject = binding.etDetailPro.text.toString()

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(formateDate)!!
            )

            if (detailProject.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Debes poner los detalles del proyecto",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //CREA EL PROYECTO
                createProject(
                    nameProject,
                    description,
                    priority,
                    date,
                    duration,
                    languageId,
                    detailProject
                )
            }
        }
    }


    //-----------------------------------------------------FUNCIONES---------------------------------------------------------//
    //1. CREAR EL PROYECTO
    private fun createProject(
        nameProject: String, description: String, priority: String, date: String,
        duration: String, languageId: Int, detailProject: String
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val id = 0
            try {
                val app = application as MyApplicaction
                app.room.projectDao().addProject(
                    Project(
                        idProject = id,
                        nameProject = nameProject,
                        description = description,
                        priority = priority,
                        data = date,
                        hours = duration,
                        languageId = languageId,
                        detailProject = detailProject
                    )
                )
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Proyecto creado", Toast.LENGTH_SHORT).show()
                    navigateToInitActivity()
                }
            } catch (e: Exception) {
                Log.e("El error del projecto", "Es ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al crear el project",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToInitActivity()
                }
            }
        }
    }

    //2. NAVEGAR A LA ACTIVIDAD PRINCIPAL TOOLBAR / LISTA PROYECTOS
    private fun navigateToInitActivity() {
        val intent = Intent(requireContext(), SampleProjectsActivity::class.java)
        startActivity(intent)
    }
}