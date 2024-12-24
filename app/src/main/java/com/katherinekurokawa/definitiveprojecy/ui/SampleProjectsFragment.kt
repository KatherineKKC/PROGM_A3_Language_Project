package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.adapter.ProjectAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentSampleProjectsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SampleProjectsFragment : Fragment() {
    private lateinit var _binding: FragmentSampleProjectsBinding
    private val binding: FragmentSampleProjectsBinding get() = _binding
    private lateinit var applicaction: MyApplicaction
    private lateinit var adapter : ProjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSampleProjectsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ADAPTER
        adapter = ProjectAdapter(mutableListOf())
        binding.rcProjects.adapter = adapter
        binding.rcProjects.layoutManager = LinearLayoutManager(requireContext())


        //APLICACION
        applicaction = requireActivity().application as MyApplicaction
        getProjects()


    }

    private fun deleteAllLProjects() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                applicaction.room.projectDao().delleteAllLprojects()
                withContext(Dispatchers.Main) {
                    // Limpia la lista en el adaptador
                    adapter.listProject.clear()
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


    private fun getProjects (){
        lifecycleScope.launch(Dispatchers.IO){
            try {
                val listProjects = applicaction.room.projectDao().getProjectsWithLanguages()
                withContext(Dispatchers.Main){
                    if(listProjects.isEmpty()){
                        binding.tvMessageProject.isVisible = true
                        Toast.makeText(requireContext(),"La lista de proyectos esta vacía", Toast.LENGTH_SHORT).show()
                    }else{
                        binding.tvMessageProject.isVisible = false

                        adapter.submitList(listProjects)
                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Log.e("Lista de projectos", "Error ${e.message}")
                    Toast.makeText(requireContext(),"Error al obtener la lista ", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

}