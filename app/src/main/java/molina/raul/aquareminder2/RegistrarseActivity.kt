package molina.raul.aquareminder2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registrarse.*

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btn_botonRegistro.setOnClickListener {
            valida_registro()
        }

    }

    private fun valida_registro() {
        //var nombre: String = et_nombreRegistro.text.toString()
        //var apellido: String = et_apellidoRegistro.text.toString()
        var correo: String = et_correoRegistro.text.toString()
        var contra1: String = et_contrasenaRegistro.text.toString()
        var contra2: String = et_contrasenaRegistroConfirmar.text.toString()

        if (!correo.isNullOrBlank() && !contra1.isNullOrBlank() &&
            !contra2.isNullOrBlank()
        ) {

            if (contra1 == contra2) {
                registrarFirebase(correo, contra1)

            } else {
                Toast.makeText(
                    this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(
                this, "Ingresar datos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registrarFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    Toast.makeText(
                        baseContext, "${user?.email} Se ha creado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Ocurrió un error al registrar.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

}