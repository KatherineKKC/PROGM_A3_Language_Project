package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.adapter.LanguageAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateLanguageBinding
import com.katherinekurokawa.definitiveprojecy.entities.Language
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


        adapter = LanguageAdapter(
            mutableListOf(),
           onItemLongClick = { name ->
            removeLanguage(name)
        },
            onItemClick = { nameLang ->
                showLanguageToModify(nameLang)

            }
        )

        val message = arguments?.getBoolean("message") ?: false
        if (message){
            val newName = arguments?.getString("newName").toString().trim()
            val oldName = arguments?.getString("oldName").toString().trim()
            modifyLanguage(oldName, newName)
        }
        binding.reciclerLanguage.adapter = adapter
        binding.reciclerLanguage.layoutManager = LinearLayoutManager(requireContext())
        getListLanguage()






    }
    private fun modifyLanguage(oldName: String, newName: String) {
        if (newName.isBlank()) {
            Toast.makeText(requireContext(), "El nuevo nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val oldLanguage = applicacion.room.languageDao().getLanguage(oldName)
                if (oldLanguage != null) {
                    val updatedLanguage = Language(oldLanguage.idLanguage, newName)
                    applicacion.room.languageDao().updateName(updatedLanguage)

                    withContext(Dispatchers.Main) {
                        adapter.modifyLanguageName(updatedLanguage)
                        Toast.makeText(requireContext(), "El lenguaje se ha modificado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "El lenguaje no existe", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al modificar el lenguaje: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun showLanguageToModify(nameLang: String) {
        val args = Bundle()
        args.putString("nameLanguage", nameLang)
        findNavController().navigate(R.id.action_createLanguageFragment_to_modifyLanguageFragment, args)
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

    private fun deleteAllLanguages() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val app = requireActivity().application as MyApplicaction
                app.room.languageDao().delleteAllLanguages()

                withContext(Dispatchers.Main) {
                    // Limpia la lista en el adaptador
                    adapter.listLanguage.clear()
                    adapter.notifyDataSetChanged()

                    Toast.makeText(requireContext(), "Todos los lenguajes han sido eliminados", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al eliminar lenguajes: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
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