package com.katherinekurokawa.definitiveprojecy.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentIntroBinding
import java.io.File
import java.io.FileOutputStream

class IntroFragment : Fragment() {
    //------------------------------------------------VARIABLES-----------------------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentIntroBinding
    private val binding: FragmentIntroBinding get() = _binding


    //-----------------------------------------METODOS IMPLEMENTADOS------------------------------------------------//
    //INFLATER
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater)
        return binding.root
    }

    //LOGICA FRAGMENT
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        video()//MOSTRAR EL VIDEO DE INTRODUCCION DE LA APPLICACION
    }


    //------------------------------------------------FUNCIONES-----------------------------------------------------//
    //MOSTRAR VIDEO
    private fun video() {
        val inputStream = resources.openRawResource(R.raw.output)
        val outputFile = File(requireContext().filesDir, "output.mp4")

        if (!outputFile.exists()) { // Evitar copiar si ya existe
            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }

        }

        val videoUri = Uri.fromFile(outputFile)
        binding.vvIntro.stopPlayback()
        binding.vvIntro.setVideoURI(null)
        binding.vvIntro.setVideoURI(videoUri)

        binding.vvIntro.setOnPreparedListener {
            it.isLooping = true
            binding.vvIntro.start()
        }
    }
}