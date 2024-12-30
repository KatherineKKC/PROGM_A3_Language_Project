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
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.adapter.LanguageAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateLanguageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateLanguageFragment : Fragment() {
    //------------------------------------------VARIABLES-----------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentCreateLanguageBinding
    private val binding: FragmentCreateLanguageBinding get() = _binding

    //APLICACION Y ADAPTER
    private lateinit var applicacion: MyApplicaction
    private lateinit var adapter: LanguageAdapter

    //------------------------------------METODOS IMPLEMENTADOS-----------------------------------//
    //1. INFLATER
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateLanguageBinding.inflate(inflater)
        return binding.root
    }

    //2. LOGICA FRAGMENTO
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //INICIALIZACION DE APPLICACION
        applicacion = requireActivity().application as MyApplicaction

        //INICIALIZACION DE ADAPTADOR
        adapter = LanguageAdapter(
            mutableListOf(),
            onItemLongClick = { name ->
                removeLanguage(name)
            }, onItemClick = { nameLang ->
                showLanguageToModify(nameLang)
            }
        )

        //CONECTA EL RECYCLER Y ADAPTER
        binding.reciclerLanguage.adapter = adapter
        binding.reciclerLanguage.layoutManager = LinearLayoutManager(requireContext())

        //OBTENEMOS LA LISTA DE LENGUAJES
        getListLanguage()
    }


    //-----------------------------------------FUNCIONES------------------------------------------//

    //1. ENVIAMOS EL LENGUAJE A MODIFICAR
    private fun showLanguageToModify(nameLang: String) {
        val args = Bundle()
        args.putString("nameLanguage", nameLang)
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                var idlang = applicacion.room.languageDao().getLanguage(nameLang).idLanguage
                args.putString("idLanguage", idlang.toString())
                withContext(Dispatchers.Main) {
                    findNavController().navigate(
                        R.id.action_createLanguageFragment_to_modifyLanguageFragment,
                        args
                    )
                }
            } catch (e: Exception) {
                Log.e("Error", "Error al obtener el id del lenguaje a enviar")
            }
        }
    }

    //2.ELIMINAR LENGUAJE
    private fun removeLanguage(nameLanguage: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("¿Quieres borrar el lenguaje?")
            .setPositiveButton("Confirmar") { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val language = applicacion.room.languageDao().getLanguage(nameLanguage)
                        if (language != null) {
                            applicacion.room.languageDao().deleteLanguage(language)
                            withContext(Dispatchers.Main) {
                                adapter.removeLanguage(nameLanguage)
                                Toast.makeText(
                                    requireContext(),
                                    "Lenguaje Borrado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    "Lenguaje no encontrado",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Error al eliminar el lenguaje ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }.setNegativeButton("Cancelar ", null).show()
    }


    //3. OBTENER LA LISTA DE LENGUAJES
    private fun getListLanguage() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                var languageList = applicacion.room.languageDao().getAllLanguages()
                withContext(Dispatchers.Main) {
                    if (languageList.isEmpty()) {
                        binding.tvMesageLanguage.isVisible = true
                    } else {
                        binding.tvMesageLanguage.isVisible = false
                        adapter.submitLanguage(languageList)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al obtener la lista de lenguajes de la DB ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //4. ELIMINAR TODOS LOS LENGUAJES
    private fun deleteAllLanguages() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val app = requireActivity().application as MyApplicaction
                app.room.languageDao().deleteAllLanguages()
                withContext(Dispatchers.Main) {
                    adapter.removeAllLanguage()
                    Toast.makeText(
                        requireContext(),
                        "Todos los lenguajes han sido eliminados",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al eliminar lenguajes: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}