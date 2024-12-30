package com.katherinekurokawa.definitiveprojecy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katherinekurokawa.definitiveprojecy.databinding.ActivitySampleProjectsBinding

class SampleProjectsActivity : AppCompatActivity() {
    //------------------------------------------------VARIABLES---------------------------------------------//
    private lateinit var _binding: ActivitySampleProjectsBinding
    private val binding: ActivitySampleProjectsBinding get() = _binding

    //------------------------------------------METODOS IMPLEMENTADOS----------------------------------------//
    //INFLATER Y LOGICA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySampleProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ACCION DE NAVEGAR AL FRAGMENTO DE LENGUAJE
        binding.toolbar.setNavigationOnClickListener {
            navigateToCreateLangage()
        }
        //ACCION DE NAVEGAR AL FRAGMENTO DE CREAR PROYECTOS
        binding.btnCreateProject.setOnClickListener {
            navigatToCreateProject()
        }
    }

    //------------------------------------------------FUNCIONES---------------------------------------------//
    //1. NAVEGAR AL FRAGMENTO QUE CONTIENE LA LISTA DE LENGUAJES Y CREACION DE LENGUAJES
    private fun navigateToCreateLangage() {
        val intent = Intent(this, CreateLanguageActivity::class.java)
        startActivity(intent)
    }

    // 1. NAVEGAR A LOS FRAGMENTOS QUE RECOGEN LA INFORMACION DE UN NUEVO PROYECTO
    fun navigatToCreateProject() {
        val intent = Intent(this, CreateProjectActivity::class.java)
        startActivity(intent)
    }
}