package com.katherinekurokawa.definitiveprojecy.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateLanguage2Binding
import com.katherinekurokawa.definitiveprojecy.entities.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.katherinekurokawa.definitiveprojecy.R

class CreateLanguage2Fragment : Fragment() {

    private lateinit var _binding: FragmentCreateLanguage2Binding
    private val binding: FragmentCreateLanguage2Binding get() = _binding

    //Application
    private lateinit var applicaction: MyApplicaction


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateLanguage2Binding.inflate(inflater)
        return binding.root
    }


    //Logica
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicaction = requireActivity().application as MyApplicaction

        binding.btnCreateLanguages.setOnClickListener{
            val nameLanguage = binding.etNameNewLanguage.text.toString()
            if(nameLanguage.isEmpty() || nameLanguage.isNullOrBlank()){
                Toast.makeText(requireContext(), "Introduce el nombre del lenguaje ", Toast.LENGTH_SHORT).show()
            }else{
                createLanguage(nameLanguage)
            }
        }
    }

    private fun createLanguage(nameLanguage :String){
        lifecycleScope.launch(Dispatchers.IO){
            try{
              val languageOld = applicaction.room.languageDao().languageExist(nameLanguage)
                withContext(Dispatchers.Main){
                    if(languageOld){
                        Toast.makeText(requireContext(), "El lenguaje ya existe, Introduzca otro nombre", Toast.LENGTH_SHORT).show()
                    }else{

                        val language = Language(0, nameLanguage)
                        applicaction.room.languageDao().addLanguage(language)
                        Toast.makeText(requireContext(), "El lenguaje ha sido creado", Toast.LENGTH_SHORT).show()
                        navigateToFragment1()

                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Error al crear el lenguaje ${e.message}", Toast.LENGTH_SHORT).show()
                    navigateToInitActivity()
                }
            }
        }
    }


    private fun navigateToInitActivity(){
        val intent = Intent(requireContext(), SampleProjectsActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToFragment1(){
        findNavController().navigate(R.id.action_createLanguage2Fragment_to_createLanguageFragment)
    }

}