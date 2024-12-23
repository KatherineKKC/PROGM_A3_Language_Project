package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.adapter.LanguageAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateLanguageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateLanguageFragment : Fragment() {
 private lateinit var _binding : FragmentCreateLanguageBinding
    private val binding : FragmentCreateLanguageBinding get() = _binding
    private lateinit var applicacion: MyApplicaction

    private lateinit var adapter: LanguageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateLanguageBinding.inflate(inflater)
        return binding.root
    }

    //logica
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applicacion = requireActivity().application as MyApplicaction
        adapter = LanguageAdapter(mutableListOf()) {  name ->
            removeLanguage(name)
        }

        binding.reciclerLanguage.adapter = adapter
        binding.reciclerLanguage.layoutManager = LinearLayoutManager(requireContext())




        getListLanguage()


    }


    private fun removeLanguage(nameLanguage: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("¿Quieres borrar el lenguaje?")
            .setPositiveButton("Confirmar") { _, _->

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val language = applicacion.room.languageDao().getLanguage(nameLanguage)
                        if (language != null) {
                            applicacion.room.languageDao().deleteLanguage(language) // Eliminar de la DB
                            withContext(Dispatchers.Main) {
                                adapter.removeLanguage(nameLanguage) // Eliminar del adaptador
                                Toast.makeText(requireContext(), "Lenguaje Borrado", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Lenguaje no encontrado", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Error al eliminar el lenguaje ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.setNegativeButton("Cancelar ", null)
                .show()


    }


    private fun getListLanguage(){
        lifecycleScope.launch(Dispatchers.IO) {
            try{
                var languageList = applicacion.room.languageDao().getAllLanguages()
                withContext(Dispatchers.Main){
                    if (languageList.isEmpty()){
                        binding.tvMesageLanguage.isVisible = true
                    }else{
                        binding.tvMesageLanguage.isVisible = false
                        adapter.submitLanguage(languageList)
                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Error al obtener la lista de lenguajes de la DB ${e.message}" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}