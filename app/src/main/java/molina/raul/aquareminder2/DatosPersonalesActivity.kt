package molina.raul.aquareminder2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_datos_personales.*

class DatosPersonalesActivity : AppCompatActivity() {

    val user = Firebase.auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_personales)

        //Si user de Firebase esta en null
        if(user==null){
            //obtiene datos de Google Sign
            datosGoogleSign()
        }else{
            //si no obtiene datos de Firebase
            datosFirebase()
        }

    }

    fun datosGoogleSign(){
        val bundle = intent.extras
        if (bundle != null) {
            val nombre = bundle.getString("name")
            val correo = bundle.getString("email")
            //val foto = bundle.getString("photo")
            val foto = bundle.getInt("photo")
            //val view: ImageView = findViewById<View>(R.id.something) as ImageView
            //val view = iv_imagenusuario
            //view.setImageResourse(bundle)
            tv_nombreusuario.setText(nombre)
            tv_correousuario.setText(correo)
            iv_imagenusuario.setImageResource(foto)
        }
    }
    fun datosFirebase(){
        user?.let {
            val nombre = user.displayName
            val correo = user.email
            val foto = user.photoUrl

            tv_nombreusuario.setText(nombre)
            tv_correousuario.setText(correo)
            //iv_imagenusuario.setImageResource(foto)
            iv_imagenusuario.setImageURI(foto)
        }
    }
}