package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateProject1Binding
import com.katherinekurokawa.definitiveprojecy.R

class CreateProject1Fragment : Fragment() {
    //-----------------------------------------VARIABLES------------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentCreateProject1Binding
    private val binding: FragmentCreateProject1Binding get() = _binding

    //-----------------------------------METODOS IMPLEMENTADOS------------------------------------//
    //1. INFLATER
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProject1Binding.inflate(inflater)
        return binding.root
    }

    //LOGICA FRAGMENT
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //CHECKBOX PRIORIDAD
        val check1 = binding.cbLow
        val check2 = binding.cbMedium
        val check3 = binding.cbHigh
        var priority: String = ""

        verifyChecks(check1, check2, check3) { selectdPriority ->
            priority = selectdPriority
        }

        //ACCION BOTON PARA AVANZAR ENTRE LOS FRAGMENTOS DE LA CREACION DEL PROYECTO
        binding.btnNext.setOnClickListener {
            val nameProject = binding.etNamePro.text.toString().orEmpty().trim()
            val description = binding.etDetailPro.text.toString().orEmpty()

            if (nameProject.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Debes establecer un nombre al proyecto",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (description.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Debes poner una descripción mínima",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (priority.isBlank()) {
                Toast.makeText(requireContext(), "Elije una prioridad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //LLAMADA A LA FUNCION
            validationData(nameProject, description, priority)
        }
    }


    //----------------------------------------FUNCIONES-------------------------------------------//
    //1. VALIDA LA INFORMACIÓN CREADA POR EL USUARIO Y LA ENVIA AL SIGUIENTE FRAGMENTO
    private fun validationData(nameProject: String, description: String, priority: String) {
        if (nameProject.isNullOrBlank() && description.isNullOrBlank() && priority.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT)
                .show()
        } else {
            val args = Bundle()
            args.putString("nameProject", nameProject)
            args.putString("description", description)
            args.putString("priority", priority)
            findNavController().navigate(
                R.id.action_createProject1Fragment_to_createProject2Fragment,
                args
            )
        }
    }

    //2. VERIFICA CUAL DE LOS CHECK HA SIDO SELECCIONADO
    private fun verifyChecks(vararg checkBoxes: CheckBox, onPriorityChanged: (String) -> Unit) {
        val checkBoxList = checkBoxes.toList()
        for (checkBox in checkBoxList) {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    //PINTAR EL CHEKBOX SELECCIONADO
                    checkBox.setBackgroundColor(resources.getColor(R.color.deep_pink, null))
                    val priority = checkBox.text.toString()
                    onPriorityChanged(priority)

                    //DESMARCAR LOS DEMAS CHECKBOX Y PINTAR POR DEFECTO
                    checkBoxList.filter { it != checkBox }.forEach {
                        it.isChecked = false
                        it.setBackgroundColor(resources.getColor(R.color.bg_box_project, null))
                    }
                } else {
                    //ESTABLECE EL FONDO POR DEFECTO CUANDO SE DESMARQUE EL CHEKBOX
                    checkBox.setBackgroundColor(resources.getColor(R.color.bg_box_project, null))
                }
            }
        }
    }

}