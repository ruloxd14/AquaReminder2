package molina.raul.aquareminder2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_recordar.*

class RecuperarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordar)

        // Initialize Firebase Auth
        auth = Firebase.auth

        btn_restablecercontrasena.setOnClickListener {
            validad_recuperacion()
        }

    }

    private fun validad_recuperacion(){
        var correo: String = et_correoRecuperar.text.toString()

        if(!correo.isNullOrBlank()){
            auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Se envio un correo a $correo",
                            Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Error al enviar correo",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this,"Ingresar correo",
                Toast.LENGTH_SHORT).show()
        }
    }
}