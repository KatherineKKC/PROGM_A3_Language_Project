package com.katherinekurokawa.definitiveprojecy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.katherinekurokawa.definitiveprojecy.adapter.ViewPagerAdapterIntro
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    //------------------------------------------------------VARIABLES--------------------------------------//
    //BINDING
    private lateinit var _binding : ActivityIntroBinding
    private val binding : ActivityIntroBinding get()= _binding


    //--------------------------------------------METODOS IMPLEMENTADOS--------------------------------------//
    //INFLATER Y LOGICA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NAVEGACION ENTRE LOS FRAGMENTOS INTRO/LOGIN
        val viewPager: ViewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapterIntro(this)
        viewPager.adapter = adapter

    }
}