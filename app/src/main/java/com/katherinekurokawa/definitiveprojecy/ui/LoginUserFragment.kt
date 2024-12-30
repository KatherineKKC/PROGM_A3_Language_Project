package com.katherinekurokawa.definitiveprojecy


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.katherinekurokawa.definitiveprojecy.databinding.FragmentLoginUserBinding
import com.katherinekurokawa.definitiveprojecy.entities.User
import com.katherinekurokawa.definitiveprojecy.ui.CreateUserActivity
import com.katherinekurokawa.definitiveprojecy.ui.SampleProjectsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class LoginUserFragment : Fragment() {
    //------------------------------------------------VARIABLES-----------------------------------------------------//
    //BINDING
    private lateinit var _binding: FragmentLoginUserBinding
    private val binding: FragmentLoginUserBinding get() = _binding

    //APLICACION
    private lateinit var application: MyApplicaction
    private var currentUser: User? = null

    //-------------------------------------------METODOS IMPLEMENTADOS-------------------------------------------------//
    //INFLATER
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginUserBinding.inflate(inflater)
        return binding.root
    }

    //LOGICA FRAGMENT
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //APPLICACION INCIALIZACION
        application = requireActivity().application as MyApplicaction

        //VIDEO PARA LOGIN
        video()

        //ACCION PARA HACER LOGIN
        binding.btnLogin.setOnClickListener {
            val nameUser = binding.etUser.text.toString().trim()
            val passwordUser = binding.etPassword.text.toString().trim()
            checkUser(nameUser, passwordUser) //COMPROBAR USUARIO
        }

        //ACCION PARA NAVEGAR AL FRAGMENTO DE CREAR UN USUARIO NUEVO
        binding.btnCreateUser.setOnClickListener {
            navigateToCreateUser()
        }
    }


    //------------------------------------------------FUNCIONES----------------------------------------------------------//
    //1. NAVEGAR AL FRAGMENTO PARA CREAR NUEVO USUARIO
    fun navigateToCreateUser() {
        val intent = Intent(requireContext(), CreateUserActivity::class.java)
        startActivity(intent)
        clean() //METODO PARA LIMPIAR LAS CAJAS
    }

    //2. NAVEGAR A LA VISTA PRINCIPAL UNA VEZ SE HA CONFIRMADO EL USUARIO
    fun navigateToSampleProjectsActivity() {
        val intent = Intent(requireContext(), SampleProjectsActivity::class.java)
        startActivity(intent)
        clean()//LIMPIAR CAJAS
    }

    //3. COMPROBAR SI EL USUARIO Y PASSWORD SON CORRECTOS
    private fun checkUser(nameUser: String, passwordUser: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val user = application.room.userDao().getUserByNameAndPassword(nameUser, passwordUser)
            withContext(Dispatchers.Main) {
                if (user != null) {
                    currentUser = user
                    navigateToSampleProjectsActivity()
                    Toast.makeText(requireContext(), "Bienvenid@", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "El usuario no existen y/o los datos no coinciden",
                        Toast.LENGTH_SHORT
                    ).show()
                    clean()
                }
            }
        }
    }


    //4. LIMPIAR CAJAS DE TEXTO
    private fun clean() {
        binding.etUser.setText("")
        binding.etPassword.setText("")
    }

    //MOSTRAR EL VIDEO
    private fun video() {
        val inputStream =
            resources.openRawResource(R.raw.output2ref) // R.raw.salida apunta al archivo en res/raw
        val outputFile = File(requireContext().filesDir, "output2ref.mp4")
        if (!outputFile.exists()) { // Evitar copiar si ya existe
            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }

        val videoUri = Uri.fromFile(outputFile)
        binding.vvIntro3.stopPlayback() // Detiene cualquier reproducción anterior
        binding.vvIntro3.setVideoURI(null)
        binding.vvIntro3.setVideoURI(videoUri)
        binding.vvIntro3.setOnPreparedListener {
            it.isLooping = true
            binding.vvIntro3.start() // Inicia la reproducción automáticamente
        }
    }
}