package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityCreateProjectBinding


class CreateProjectActivity : AppCompatActivity() {
    //----------------------------------------VARIABLES------------------------------------------------//
    //BINDING
    private lateinit var _binding: ActivityCreateProjectBinding
    private val binding: ActivityCreateProjectBinding get() = _binding


    //----------------------------------------METODOS IMPLEMENTADOS------------------------------------------------//
    //VISTA Y LOGICA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
