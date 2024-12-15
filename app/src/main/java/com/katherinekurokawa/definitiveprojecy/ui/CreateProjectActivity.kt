package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityCreateProjectBinding


class CreateProjectActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityCreateProjectBinding
    private val binding: ActivityCreateProjectBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun getCollectedData(nameProject:String , description:String,priority: String): List<String> {
        return listOf(nameProject, description, priority)
    }
}
