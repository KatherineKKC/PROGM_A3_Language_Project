package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.adapter.LanguageAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentModifyLanguageBinding
import com.katherinekurokawa.definitiveprojecy.entities.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModifyLanguageFragment : Fragment() {

    //-----------------------------------------VARIABLES------------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentModifyLanguageBinding
    private val binding: FragmentModifyLanguageBinding get() = _binding

    //APPLICACION/ADAPTER
    private lateinit var applicaction: MyApplicaction
    private lateinit var adapter: LanguageAdapter


    //-------------------------------------METODOS IMPLEMENTADOS-----------------------------------//
    //INFLATER FRAGMENT
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentModifyLanguageBinding.inflate(inflater)
        return binding.root
    }

    //LOGICA FRAGMENTO
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //APLICACION
        applicaction = requireActivity().application as MyApplicaction

        //RECIBIMOS DATOS DEL LENGUAJE A MODIFICAR
        val nameOld = arguments?.getString("nameLanguage").toString().trim()
        binding.etNameModify.setText(nameOld)
        val idLanguage = arguments?.getString("idLanguage").toString().trim()

        //ACCION BOTON MODIFICAR
        binding.btnModifyLanguage.setOnClickListener {
            val nameNewLanguage = binding.etNameModify.text.toString().trim()
            if (nameNewLanguage.isBlank() || nameNewLanguage == nameOld) {
                Toast.makeText(requireContext(), "No se realizó ningún cambio", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_modifyLanguageFragment_to_createLanguageFragment)
            } else {
                modifyLanguage(idLanguage, nameNewLanguage)
            }
        }

    }


    //-------------------------------------------FUNCIONES----------------------------------------//
    //1. MODIFICAR EL LENGUAJE RECIBIDO
    private fun modifyLanguage(idLang: String, nameNew: String) {
        if (idLang.isBlank() || nameNew.isBlank()) {
            Toast.makeText(
                requireContext(),
                "Los datos no estan completos $idLang o $nameNew ",
                Toast.LENGTH_SHORT
            ).show()

        }
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val language = applicaction.room.languageDao().getLanguage(nameNew)
                if (language != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "El lenguaje ya existe elige otro ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        val languageM = Language(
                            idLanguage = idLang.toInt(),
                            name = nameNew
                        )
                        applicaction.room.languageDao().updateLanguage(languageM)
                        findNavController().navigate(R.id.action_modifyLanguageFragment_to_createLanguageFragment)
                    }


                }

            } catch (e: Exception) {
                Log.e("lenguaje", "error ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al verificar si el lenguaje existe ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}