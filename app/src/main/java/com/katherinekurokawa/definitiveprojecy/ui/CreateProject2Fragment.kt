package com.katherinekurokawa.definitiveprojecy.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentCreateProject2Binding
import com.katherinekurokawa.definitiveprojecy.entities.Language


class CreateProject2Fragment : Fragment() {
    private lateinit var _binding: FragmentCreateProject2Binding
    private val binding: FragmentCreateProject2Binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateProject2Binding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDate.setOnClickListener{
            showCalendar()
        }

        val date = binding.etDate.text.toString()
        val duration = binding.etDuration.text.toString()
        validationData(date,duration)







    }

    private fun validationData(date: String, duration: String){
        var languageId: Int =0
        val selectedanguage = binding.spinnerLanguage.selectedItem as? Language
        if(selectedanguage != null){
            languageId = selectedanguage.idLanguage
        }

        val message = arguments?.getBoolean("dataProject") ?:false
        val fragmentFinal = CreateProject3Fragment()
        val args = Bundle()

        if(date.isNullOrBlank()  || duration.isNullOrBlank() || languageId <= 0) {
            if (message) {
                args.putString("message", "Los datos son incompletos")
            }

        }else {
            if(message) {
                args.putString("message", "Los datos son incompletos")
                args.putString("date", date)
                args.putString("duration", duration)
                args.putString("laguageId", languageId.toString())
            }
        }
        fragmentFinal.arguments = args
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fc_create_project2_fragment, fragmentFinal)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()

    }




    private fun showCalendar() {
         val calendar = Calendar.getInstance()
         val year =  calendar.get(Calendar.YEAR)
         val month = calendar.get(Calendar.MONTH)
         val day = calendar.get(Calendar.DAY_OF_MONTH)
         val datePickerDialog = DatePickerDialog(
             requireContext(),
             R.style.CustomDatePickerDialog,
             {_,year, month,dayOfMonth ->
                 val dateSelect = "$dayOfMonth/${month + 1}/$year"
                 binding.etDate.setText(dateSelect)
             },
             year,month,day
         )
        datePickerDialog.setOnShowListener {
            val window = datePickerDialog.window
            val decorView = window?.decorView
            val header = decorView?.findViewById<View>(resources.getIdentifier("date_picker_header", "id", "android"))
            header?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.school_bus_yellow))

        }
        datePickerDialog.show()
     }



}

