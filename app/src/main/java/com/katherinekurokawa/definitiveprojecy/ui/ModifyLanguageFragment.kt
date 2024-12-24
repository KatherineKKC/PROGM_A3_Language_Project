package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.adapter.LanguageAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentModifyLanguageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModifyLanguageFragment : Fragment() {
  private lateinit var _binding : FragmentModifyLanguageBinding
    private val binding : FragmentModifyLanguageBinding get() = _binding
    private lateinit var applicaction: MyApplicaction
    private lateinit var adapter: LanguageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentModifyLanguageBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applicaction = requireActivity().application as MyApplicaction

        val nameOld = arguments?.getString("nameLanguage").toString().trim()
        binding.etNameModify.setText(nameOld)

        binding.btnModifyLanguage.setOnClickListener {
            val nameNewLanguage = binding.etNameModify.text.toString().trim()

            if (nameNewLanguage.isBlank() || nameNewLanguage == nameOld) {
                Toast.makeText(requireContext(), "No se realizó ningún cambio", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_modifyLanguageFragment_to_createLanguageFragment)
            } else {
               modifyLanguage(nameOld, nameNewLanguage)
            }
        }

    }

    private fun modifyLanguage (oldName: String, nameNew :String){

            lifecycleScope.launch(Dispatchers.IO){
                try {
                   val exist=  applicaction.room.languageDao().languageExist(nameNew)

                    withContext(Dispatchers.Main){
                        if (exist){
                            Toast.makeText(requireContext(), "El lenguaje ya existe elige otro ", Toast.LENGTH_SHORT).show()
                        }else{
                            val argumentsToModify = Bundle()

                            argumentsToModify.putBoolean("message", true)
                            argumentsToModify.putString("oldName", oldName)
                            argumentsToModify.putString("newName",nameNew)
                            findNavController().navigate(R.id.action_modifyLanguageFragment_to_createLanguageFragment, argumentsToModify)
                        }

                    }
                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                            Toast.makeText(requireContext(), "Error al verificar si el lenguaje existe ", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }



}