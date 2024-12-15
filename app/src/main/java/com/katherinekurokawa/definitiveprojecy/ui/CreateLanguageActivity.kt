package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityCreateLanguageBinding


class CreateLanguageActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityCreateLanguageBinding
    private val binding : ActivityCreateLanguageBinding get()= _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}