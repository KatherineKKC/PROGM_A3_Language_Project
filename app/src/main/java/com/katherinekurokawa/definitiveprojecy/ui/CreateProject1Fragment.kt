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

        val nameProject = binding.etNamePro.text.toString().orEmpty()
        val description = binding.etDetailPro.text.toString().orEmpty()
        val check1 = binding.cbLow
        val check2 = binding.cbMedium
        val check3 = binding.cbHigh
        var priority : String = ""
        verifyChecks(check1, check2, check3) { selectdPriority ->
            priority = selectdPriority
        }


    }

    private fun validationData(nameProject: String, description: String, priority: String) {
        val message = arguments?.getBoolean("dataProject") ?:false
        val fragmentFinal = CreateProject3Fragment()
        val args = Bundle()

        if (nameProject.isNullOrBlank() && description.isNullOrBlank() && priority.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
        }else{
              val intent = Intent(requireContext(), CreateProjectActivity::class.java)
              intent.putExtra("nameProject", nameProject)
              intent.putExtra("description", description)
              intent.putExtra("priority", priority)

        }
        fragmentFinal.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.fc_create_project1_fragment, fragmentFinal)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
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