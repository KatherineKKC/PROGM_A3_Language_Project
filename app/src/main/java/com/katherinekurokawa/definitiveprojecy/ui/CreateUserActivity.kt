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
    private lateinit var _binding : ActivityCreateUserBinding
    private val binding : ActivityCreateUserBinding get()= _binding
    private lateinit var applicaction: MyApplicaction
    private  var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        video()

        binding.btnCreateUser.setOnClickListener{
            //Aplicaciton
            val nameUser = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordConfirm = binding.etPasswordConfirm.text.toString()
            //Confirmamos que los campos no esten vacios
            if(nameUser.isBlank() || password.isBlank() || passwordConfirm.isBlank()){
                Snackbar.make(binding.root, "Debes llenar todos los campos", Snackbar.LENGTH_SHORT).show()
            }else{
                saveUser(nameUser, password,passwordConfirm)
            }
        }


    }


    //Crea usuario guardar en la bd
    private fun saveUser(nameUser: String, password: String, passwordConfirm: String){
        val app =  application as  MyApplicaction

        if (password.trim() == passwordConfirm.trim()){
            var id: Int = 0
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
                        Toast.makeText(applicationContext, "El usuario ha sido creado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error al crear el usuario", Toast.LENGTH_SHORT).show()

                }
            }
        }else{
            Snackbar.make(binding.root, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()

        }

    }





    //FUNCION DE VIDEO
    private fun video(){
        val inputStream = resources.openRawResource(R.raw.output3) // R.raw.salida apunta al archivo en res/raw
        val outputFile = File(filesDir, "output3.mp4")

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