package com.katherinekurokawa.definitiveprojecy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.adapter.ProjectAdapter
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentSampleProjectsBinding
import com.katherinekurokawa.definitiveprojecy.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SampleProjectsFragment : Fragment() {

    private lateinit var _binding: FragmentSampleProjectsBinding
    private val binding: FragmentSampleProjectsBinding get() = _binding
    private lateinit var applicaction: MyApplicaction
    private lateinit var adapter: ProjectAdapter

    // Inflar y configurar el layout del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSampleProjectsBinding.inflate(inflater)
        return binding.root


    }
    

    //////////////////////////////////////////////////////////////////LOGICA/////////////////////////////////////////////////
    

    // Configuración inicial del fragmento, como el adapter y la obtención de proyectos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicialización del adaptador
        adapter = ProjectAdapter(
            mutableListOf(),
            btnOnItemClick = { idProject -> deleteProject(idProject) },
            onItemClickModify = { idProject -> navigateToModify(idProject) }
        )

        // Configuración del RecyclerView
        binding.rcProjects.adapter = adapter
        binding.rcProjects.layoutManager = LinearLayoutManager(requireContext())

        // Acceder a la instancia de la aplicación y obtener proyectos
        applicaction = requireActivity().application as MyApplicaction
        getProjects()
    }
    
    
    
    /////////////////////////////////////////////////////FUNCIONES ////////////////////////////////////////////////////////////////////////
    

    // Función para obtener la lista de proyectos desde la base de datos
    private fun getProjects() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val listProjects = applicaction.room.projectDao().getProjectsWithLanguages()
                withContext(Dispatchers.Main) {
                    if (listProjects.isEmpty()) {
                        binding.tvMessageProject.isVisible = true
                        Toast.makeText(requireContext(), "La lista de proyectos está vacía", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.tvMessageProject.isVisible = false
                        adapter.submitList(listProjects)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("Lista de proyectos", "Error: ${e.message}")
                    Toast.makeText(requireContext(), "Error al obtener la lista de proyectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // Función para navegar al Activity de modificación de proyecto
    private fun navigateToModify(idPro: Int) {
        if (idPro == -1) {
            Toast.makeText(requireContext(), "El ID del proyecto es inválido", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val projectToModify = applicaction.room.projectDao().getProjectWithId(idPro)




                    val bundle = Bundle().apply {
                            putString("nameProject", projectToModify.project.nameProject)
                            putString("description", projectToModify.project.description)
                            putString("date", projectToModify.project.data)
                            putString("priority", projectToModify.project.priority)
                            putString("duration", projectToModify.project.hours)
                            putString("language", projectToModify.language?.idLanguage.toString())
                            putString("detail", projectToModify.project.detailProject)
                        }
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(R.id.action_sampleProjectsFragment_to_projectModifyFragment,bundle)
                            Toast.makeText(requireContext(), "El id del lenguaje es ${projectToModify.language?.idLanguage}", Toast.LENGTH_SHORT).show()


                        }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("Error al obtener proyecto", "${e.message}")
                        Toast.makeText(requireContext(), "Error al obtener los datos del proyecto a modificar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Función para eliminar un proyecto
    private fun deleteProject(idDeleteProject: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                applicaction.room.projectDao().removeProject(idDeleteProject)
                withContext(Dispatchers.Main) {
                    adapter.deleteProject(idDeleteProject)
                    Toast.makeText(requireContext(), "El proyecto ha sido eliminado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al eliminar el proyecto: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}
