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
   private lateinit var _binding: FragmentLoginUserBinding
    private val binding: FragmentLoginUserBinding get() = _binding
    //Iniciamos la aplicacion para poder acceder a la base de dato
    private lateinit var application : MyApplicaction
    private  var currentUser: User? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentLoginUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        video() //Reproducimos el video

        //Cuando se haga click sobre el btn iniciar sesion se enviaran los datos
        binding.btnLogin.setOnClickListener{
            val nameUser = binding.etUser.text.toString().trim()
            val passwordUser = binding.etPassword.text.toString().trim()
            checkUser(nameUser,passwordUser)
        }

        binding.btnCreateUser.setOnClickListener{
            navigateToCreateUser()
        }


    }



    fun navigateToCreateUser() {
        val intent = Intent(requireContext(), CreateUserActivity::class.java)
        startActivity(intent)
        clean()

    }

    fun navigateToSampleProjectsActivity(){
        val intent = Intent(requireContext(), SampleProjectsActivity::class.java)
        startActivity(intent)
        clean()
    }

    //Comprobar si el usuario existe y la contraseña son correctas
    private fun checkUser(nameUser: String, passwordUser : String){
        lifecycleScope.launch(Dispatchers.IO) {
             application = requireActivity().application as MyApplicaction
             val user = application.room.userDao().getUserByNameAndPassword(nameUser,passwordUser)
            Log.e("Los datos", "Se estan consultando correctamente ")

            withContext(Dispatchers.Main) {
                if (user!=null) {
                        currentUser = user
                        navigateToSampleProjectsActivity()
                    Toast.makeText(requireContext(), "Bienvenid@", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "El usuario no existen y/o los datos no coinciden", Toast.LENGTH_SHORT).show()
                    clean()
                }
            }


        }
    }


    //funcion para limpiar cajas
    private fun clean(){
        binding.etUser.setText("")
        binding.etPassword.setText("")
    }




    //FUNCION DE VIDEO
    private fun video(){
        val inputStream = resources.openRawResource(R.raw.output2ref) // R.raw.salida apunta al archivo en res/raw
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