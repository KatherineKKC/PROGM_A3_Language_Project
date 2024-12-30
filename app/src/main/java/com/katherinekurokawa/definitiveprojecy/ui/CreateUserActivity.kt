package com.katherinekurokawa.definitiveprojecy.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.katherinekurokawa.definitiveprojecy.MyApplicaction
import com.katherinekurokawa.definitiveprojecy.databinding.ActivityCreateUserBinding
import java.io.File
import java.io.FileOutputStream
import com.katherinekurokawa.definitiveprojecy.R
import com.katherinekurokawa.definitiveprojecy.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateUserActivity : AppCompatActivity() {
    //-----------------------------------------------VARIABLES-----------------------------------------------------//
    //BINDING
    private lateinit var _binding: ActivityCreateUserBinding
    private val binding: ActivityCreateUserBinding get() = _binding

    //----------------------------------------METODOS IMPLEMENTADOS------------------------------------------------//
    //INFLATER Y LOGICA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ENSEÑA LA ANIMACION EN LA VISTA DE CREAR USUARIO
        video()

        //ACCION DEL BOTON CREAR USUARIO
        binding.btnCreateUser.setOnClickListener {
            val nameUser = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordConfirm = binding.etPasswordConfirm.text.toString()
            //VERIFICA QUE EL FORMULARIO ESTE LLENO
            if (nameUser.isBlank() || password.isBlank() || passwordConfirm.isBlank()) {
                Snackbar.make(binding.root, "Debes llenar todos los campos", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                //AÑADE EL USUARIO
                saveUser(nameUser, password, passwordConfirm)
            }
        }


    }


    //-------------------------------------------------FUNCIONES----------------------------------------------------//
    //1. CREAR EL NUEVO USUARIO
    private fun saveUser(nameUser: String, password: String, passwordConfirm: String) {
        val app = application as MyApplicaction
        if (password.trim() == passwordConfirm.trim()) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    app.room.userDao().addUser(
                        User(
                            id = 0, // AutoIncrement se encargará del ID
                            nameUser = nameUser.trim(),
                            passwordUser = password.trim()
                        )
                    )
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "El usuario ha sido creado",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Error al crear el usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Snackbar.make(binding.root, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    //2. MOSTRAR EL VIDEO
    private fun video() {
        val inputStream = resources.openRawResource(R.raw.output3)
        val outputFile = File(filesDir, "output3.mp4")
        if (!outputFile.exists()) {
            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }

        }
        val videoUri = Uri.fromFile(outputFile)
        binding.vvIntro3.stopPlayback()
        binding.vvIntro3.setVideoURI(null)
        binding.vvIntro3.setVideoURI(videoUri)

        binding.vvIntro3.setOnPreparedListener {
            it.isLooping = true
            binding.vvIntro3.start()
        }

    }
}