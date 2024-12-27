package com.katherinekurokawa.definitiveprojecy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentProjectModifyBinding
import com.katherinekurokawa.definitiveprojecy.entities.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectModifyFragment : Fragment() {
    //Binding
    private lateinit var _binding:FragmentProjectModifyBinding
    private val binding get() = _binding
    //Variables
    private lateinit var applicactionI: MyApplicaction
    private lateinit var languageList: List<Language>
    private var selectedLanguage: Int? = null

    //Vista
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProjectModifyBinding.inflate(inflater)
        return binding.root
    }



    //////////////////////////////////////////////////////////////////LOGICA/////////////////////////////////////////////////



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Apliacacion
        applicactionI = requireActivity().application as MyApplicaction

        // 1. Obtener los datos recibidos desde el intent
        val nameProject = arguments?.getString("nameProject").orEmpty().trim()
        val description = arguments?.getString("description").orEmpty().trim()
        val date = arguments?.getString("date").orEmpty().trim()
        val priority = arguments?.getString("priority").orEmpty().trim()
        val duration = arguments?.getString("duration").orEmpty().trim()
        val language = arguments?.getInt("language", -1)
        val detailProject = arguments?.getString("detail").orEmpty().trim()



        // 2. Mostrar los datos del proyecto en la UI
        if (language == -1) {
            showProject(nameProject, description, date, priority, duration, detailProject, language)
        }

        // 3. Configurar los CheckBoxes para el nivel de prioridad
        var priorityToModify = ""
        val check1 = binding.cbLowModify
        val check2 = binding.cbMediumModify
        val check3 = binding.cbHeighModify
        verifyChecks(check1, check2, check3) { selectedPriority ->
            priorityToModify = selectedPriority
        }

        // 4. Configurar el Spinner de Lenguajes
        val spinner: Spinner = binding.spinnerModifier
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Verifica que la lista de lenguajes no esté vacía
                if (::languageList.isInitialized && position >= 0 && position < languageList.size) {
                    selectedLanguage = languageList[position].idLanguage
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Seleccion", "Aún no se selecciona lenguaje")
            }
        }

        // 5. Cargar los lenguajes en el Spinner
        showSpinner()

        // 6. Enviar los datos modificados cuando se presione el botón
        binding.btnModify.setOnClickListener {
            selectedLanguage?.let {
                sendDataModifier(
                    binding.tvTitleModifyProject.text.toString(),
                    priorityToModify,
                    binding.etDurationModify.text.toString().trim(),
                    binding.etDetailModify.text.toString(),
                    it
                )
            }
        }
    }




    /////////////////////////////////////////////////////FUNCIONES ////////////////////////////////////////////////////////////////////////


    // 7. Función para enviar los datos modificados
    private fun sendDataModifier(nameProjectM: String, priorityM: String, durationM: String, detailProjectM: String, idLang: Int) {
        if (priorityM.isBlank() || durationM.isBlank() || detailProjectM.isBlank() || idLang == -1) {
            navigateUpToSampleActivity()
            Snackbar.make(binding.root, "No se realizó ningún cambio", Snackbar.LENGTH_SHORT).show()
        } else {
            val bundle = Bundle().apply {
                putString("nameProject", nameProjectM)
                putString("priority", priorityM)
                putString("duration", durationM)
                putString("detail", detailProjectM)
                putInt("language", idLang)
            }
            findNavController().navigate(R.id.action_projectModifyFragment_to_sampleProjectsFragment, bundle)


        }
    }


    // 8. Navegar a la actividad de proyectos si no se realiza ningún cambio
    private fun navigateUpToSampleActivity() {
        findNavController().navigate(R.id.action_projectModifyFragment_to_sampleProjectsFragment)
    }

    private fun showSpinner() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Obtener los lenguajes de la base de datos
                val listLanguage = applicactionI.room.languageDao().getAllLanguages()
                languageList = listLanguage

                // Obtener los nombres de los lenguajes
                val languageNames = if (listLanguage.isEmpty()) {
                    listOf("No existe ningún lenguaje")
                } else {
                    listLanguage.map { it.name }
                }

                withContext(Dispatchers.Main) {
                    // Aquí seleccionamos el lenguaje recibido en los argumentos
                    val languageId = arguments?.getInt("language", -1)
                    if (languageId != -1) {
                        // Encontrar la posición del lenguaje en la lista
                        val languagePosition = languageList.indexOfFirst { it.idLanguage == languageId }
                        if (languagePosition != -1) {
                            // Seleccionar el idioma correspondiente en el Spinner
                            if (languageId != null) {
                                binding.spinnerModifier.setSelection(languageId)
                            }
                        }
                    }
                    // Configurar el adaptador del Spinner
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.spinner_layout,
                        languageNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerModifier.adapter = adapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, "Error al obtener la lista de lenguajes", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 10. Función para mostrar los datos del proyecto en los campos
    private fun showProject(nameProject: String, description: String, date: String, priority: String, duration: String, detailProject: String, language: Int) {
        if (nameProject.isBlank() || description.isBlank() || date.isBlank() || duration.isBlank() || detailProject.isBlank()) {
            Toast.makeText(requireContext(), "Los campos recibidos del fragmento están vacíos $language", Toast.LENGTH_SHORT).show()
        } else {

            // Configurar los CheckBoxes según la prioridad
            when (priority) {
                "Baja" -> {
                    binding.cbLowModify.isChecked = true
                    setCheckBoxColors(binding.cbLowModify, binding.cbMediumModify, binding.cbHeighModify)
                }
                "Media" -> {
                    binding.cbMediumModify.isChecked = true
                    setCheckBoxColors(binding.cbMediumModify, binding.cbLowModify, binding.cbHeighModify)
                }
                "Alta" -> {
                    binding.cbHeighModify.isChecked = true
                    setCheckBoxColors(binding.cbHeighModify, binding.cbLowModify, binding.cbMediumModify)
                }
            }

            // Asignar los datos a los campos de texto

            binding.tvTitleModifyProject.setText(nameProject)
            binding.tvDescriptionModify.setText(description)
            binding.tvDateModifyProg.setText(date)
            binding.etDurationModify.setText(duration)

            binding.etDetailModify.setText(detailProject)
        }
    }

    // 11. Configurar el color de los CheckBoxes seleccionados
    private fun setCheckBoxColors(selected: CheckBox, vararg others: CheckBox) {
        selected.setBackgroundColor(resources.getColor(R.color.deep_pink, null))
        others.forEach { it.setBackgroundColor(resources.getColor(R.color.texto_hint, null)) }
    }

    // 12. Función para verificar el cambio de prioridad en los CheckBoxes
    private fun verifyChecks(vararg checkBoxes: CheckBox, onPriorityChanged: (String) -> Unit) {
        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Cambiar color del CheckBox seleccionado
                    checkBox.setBackgroundColor(resources.getColor(R.color.deep_pink, null))

                    // Obtener el texto del CheckBox seleccionado
                    val priority = checkBox.text.toString()
                    onPriorityChanged(priority)

                    // Desmarcar y restaurar el fondo de los demás CheckBoxes
                    checkBoxes.filter { it != checkBox }.forEach {
                        it.isChecked = false
                        it.setBackgroundColor(resources.getColor(R.color.texto_hint, null))
                    }
                } else {
                    checkBox.setBackgroundColor(resources.getColor(R.color.texto_hint, null))
                }
            }
        }
    }
}