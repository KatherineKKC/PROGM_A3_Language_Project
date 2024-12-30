package com.katherinekurokawa.definitiveprojecy.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateProject2Binding
import com.katherinekurokawa.definitiveprojecy.entities.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateProject2Fragment : Fragment() {
    //------------------------------------------------VARIABLES----------------------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentCreateProject2Binding
    private val binding: FragmentCreateProject2Binding get() = _binding

    //APLICACION
    private lateinit var applicaction: MyApplicaction

    //LISTA DE LENGUAJES Y BUNDLE
    private lateinit var languageList: List<Language>
    private val args3 = Bundle()


    //--------------------------------------------METODOS IMPLEMENTADOS------------------------------------------//
    //INFLATER
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProject2Binding.inflate(inflater)
        return binding.root
    }

    //LOGICA FRAGMENT
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //APLICACION
        applicaction = requireActivity().application as MyApplicaction

        //ACCION PARA MOSTRAR EL CALENDARIO
        binding.etDate.setOnClickListener {
            showCalendar()
        }

        //MOSTRAR EL SPINNER /LISTA DE LENGUAJES
        showSpinner()
        val spinner: Spinner = binding.spinnerLanguage
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (::languageList.isInitialized && position >= 0 && position < languageList.size) {
                    val selectedLanguage = languageList[position]
                    val idLang = selectedLanguage.idLanguage
                    if (idLang == -1) {
                        Toast.makeText(
                            requireContext(),
                            "no se obtuvo el id del lenguaje seleccionado",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        args3.putInt("languageId", idLang) //ENVIAMOS EL ID AL SIGUIENTE FRAGMENTO
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("Seleccion", "Aún no se selecciona lenguaje")
            }
        }

        //ACCION BOTON PARA CONTINUAR AL SIGUIENTE FRAGMENTO DE LA CREACION DEL PROYECTO
        binding.btnNext2.setOnClickListener {
            val date = binding.etDate.text.toString()
            val duration = binding.etDuration.text.toString()
            if (date.isBlank()) {
                Toast.makeText(requireContext(), "Debes elegir una fecha", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (duration.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Debes establecer una duración",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            //FUNCION PARA VALIDAR Y ENVIAR LOS DATOS AL SIGUIENTE FRAGMENTO
            validationData(date, duration)
        }
    }


    //-------------------------------------------------VARIABLES----------------------------------------------------------//
    //1. RECIBIR LOS DATOS DEL FRAGMENTO ANTERIOR Y ENVIARLOS AL FRAGMENTO 3(ULTIMO) PARA LA CREACION DEL PROYECTO
    private fun validationData(date: String, duration: String) {
        val nameProject = arguments?.getString("nameProject").toString()
        val description = arguments?.getString("description").toString()
        val priority = arguments?.getString("priority").toString()

        args3.putString("nameProject", nameProject)
        args3.putString("description", description)
        args3.putString("priority", priority)
        args3.putString("message", "Los datos son incompletos")
        args3.putString("date", date)
        args3.putString("duration", duration)

        findNavController().navigate(
            R.id.action_createProject2Fragment_to_createProject3Fragment,
            args3
        )
    }


    //2. MOSTRAR LA LISTA DE LENGUAJES
    private fun showSpinner() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listLanguage = applicaction.room.languageDao().getAllLanguages()
                if (!listLanguage.isEmpty()) {
                    languageList = listLanguage
                }
                val listaNombres = if (listLanguage.isEmpty()) {
                    listOf("No existe ningún lenguaje")
                } else {
                    listLanguage.map { it.name }

                }

                withContext(Dispatchers.Main) {
                    val spinner: Spinner = binding.spinnerLanguage

                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.spinner_layout,
                        listaNombres
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar lenguajes: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    //3. MOSTRAR EL CALENDARIO Y ASIGNARLE EL ESTILO CREADO
    private fun showCalendar() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                val dateSelect = "$dayOfMonth/${month + 1}/$year"
                binding.etDate.setText(dateSelect)
            },
            year, month, day
        )
        datePickerDialog.setOnShowListener {
            val window = datePickerDialog.window
            val decorView = window?.decorView
            val header = decorView?.findViewById<View>(
                resources.getIdentifier(
                    "date_picker_header",
                    "id",
                    "android"
                )
            )
            header?.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.school_bus_yellow
                )
            )
        }
        datePickerDialog.show()
    }
}

