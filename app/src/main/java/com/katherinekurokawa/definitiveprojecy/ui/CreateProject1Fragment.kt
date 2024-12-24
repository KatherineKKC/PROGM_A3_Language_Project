package com.katherinekurokawa.definitiveprojecy.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateProject1Binding
import com.katherinekurokawa.definitiveprojecy.R

class CreateProject1Fragment : Fragment() {

    private lateinit var _binding: FragmentCreateProject1Binding
    private val binding : FragmentCreateProject1Binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProject1Binding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val check1 = binding.cbLow
        val check2 = binding.cbMedium
        val check3 = binding.cbHigh

        var priority : String = ""

        verifyChecks(check1, check2, check3) { selectdPriority ->
            priority = selectdPriority
        }

        binding.btnNext.setOnClickListener{
            val nameProject = binding.etNamePro.text.toString().orEmpty().trim()
            val description = binding.etDetailPro.text.toString().orEmpty()


            if (nameProject.isBlank()){
                Toast.makeText(requireContext(), "Debes establecer un nombre al proyecto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(description.isBlank()){
                Toast.makeText(requireContext(), "Debes poner una descripción mínima", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(priority.isBlank()){
                Toast.makeText(requireContext(), "Elije una prioridad", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
            }
            validationData(nameProject,description,priority)
        }




    }

    private fun validationData(nameProject: String, description: String, priority: String) {
        if (nameProject.isNullOrBlank() && description.isNullOrBlank() && priority.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
            val args = Bundle()
            args.putString("nameProject", nameProject)
            args.putString("description", description)
            args.putString("priority", priority)
            findNavController().navigate(R.id.action_createProject1Fragment_to_createProject2Fragment, args)
        }

    }





    private fun verifyChecks(vararg checkBoxes: CheckBox, onPriorityChanged: (String) -> Unit) {
        val checkBoxList = checkBoxes.toList()

        for (checkBox in checkBoxList) {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Cambiar color del CheckBox seleccionado
                    checkBox.setBackgroundColor(resources.getColor(R.color.deep_pink, null))

                    // Obtener el texto del CheckBox seleccionado
                    val priority = checkBox.text.toString()
                    onPriorityChanged(priority) // Llamar al callback con la prioridad seleccionada

                    // Desmarcar y restaurar el fondo de los demás CheckBoxes
                    checkBoxList.filter { it != checkBox }.forEach {
                        it.isChecked = false
                        it.setBackgroundColor(resources.getColor(R.color.bg_box_project, null))
                    }
                } else {
                    // Restaurar fondo si se desmarca
                    checkBox.setBackgroundColor(resources.getColor(R.color.bg_box_project, null))
                }
            }
        }
    }

}