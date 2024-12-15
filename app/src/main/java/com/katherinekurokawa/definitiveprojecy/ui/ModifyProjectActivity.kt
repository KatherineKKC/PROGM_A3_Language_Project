package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityModifyProjectBinding


class ModifyProjectActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityModifyProjectBinding
    private val binding: ActivityModifyProjectBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityModifyProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
