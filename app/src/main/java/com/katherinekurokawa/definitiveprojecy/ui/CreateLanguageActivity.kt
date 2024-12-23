package com.katherinekurokawa.definitiveprojecy.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityCreateLanguageBinding
import com.katherinekurokawa.definitiveprojecy.R

class CreateLanguageActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityCreateLanguageBinding
    private val binding : ActivityCreateLanguageBinding get()= _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fbtnCreateNewLanguage.setOnClickListener{
            navigateToCreateNewLanguage()
        }

        binding.fbtnBack.setOnClickListener {
            navigateToInitActivity()
        }
    }

    private fun navigateToInitActivity() {
       val intent = Intent(this, SampleProjectsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCreateNewLanguage() {
        val navController = findNavController(R.id.fragmentContainerView2)
        navController.navigate(R.id.action_createLanguageFragment_to_createLanguage2Fragment)
    }
}