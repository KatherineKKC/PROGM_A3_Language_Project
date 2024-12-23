package com.katherinekurokawa.definitiveprojecy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katherinekurokawa.definitiveprojecy.databinding.ActivitySampleProjectsBinding
import com.katherinekurokawa.definitiveprojecy.R

class SampleProjectsActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySampleProjectsBinding
    private val binding: ActivitySampleProjectsBinding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySampleProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toolbar.setNavigationOnClickListener {
            navigateToCreateLangage()
        }



        binding.btnCreateProject.setOnClickListener{
            navigatToCreateProject()
        }

    }

    private fun navigateToCreateLangage() {
        val intent = Intent(this, CreateLanguageActivity::class.java)
        startActivity(intent)
    }

    fun navigatToCreateProject() {
        val intent = Intent(this, CreateProjectActivity::class.java)
        startActivity(intent)
    }
}