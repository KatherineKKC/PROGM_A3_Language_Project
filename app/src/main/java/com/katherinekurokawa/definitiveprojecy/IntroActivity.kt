package com.katherinekurokawa.definitiveprojecy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.katherinekurokawa.definitiveprojecy.adapter.ViewPagerAdapterIntro
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityIntroBinding
    private val binding : ActivityIntroBinding get()= _binding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewPager: ViewPager2 = binding.viewPager2
        val adapter = ViewPagerAdapterIntro(this)
        viewPager.adapter = adapter

        //Recibir datos del usuario que ha logeado para comprobar que existe

    }



}