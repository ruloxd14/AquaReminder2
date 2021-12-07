package molina.raul.aquareminder2.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_about.*
import molina.raul.aquareminder2.DatosPersonalesActivity
import molina.raul.aquareminder2.InicioSesionActivity
import molina.raul.aquareminder2.R
import molina.raul.aquareminder2.RecipienteActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        menu()

        /*val bundle = intent.extras
        if (bundle != null) {
            val nombre = bundle.getString("name")
            tv_nombreperfil.setText(nombre)
        }*/

        btn_configuraciones.setOnClickListener {

        }
        btn_unidades.setOnClickListener {

        }
        btn_idiomas.setOnClickListener {

        }
        btn_datos_per.setOnClickListener {
            val intent = Intent(this, DatosPersonalesActivity::class.java)
            startActivity(intent)
        }
       /* btn_recipientes.setOnClickListener {
            val intent = Intent(this, RecipienteActivity::class.java)
            startActivity(intent)
        }*/
        btn_cerrar_sesion.setOnClickListener {
            /*val intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)*/
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }





    }
    fun menu(){
        //Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botton_navigation)
        //set home
        bottomNavigationView.selectedItemId = (R.id.about)

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboard -> {
                    startActivity(
                        Intent(
                            applicationContext, DashboardActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home -> {
                    startActivity(
                        Intent(
                            applicationContext, MainActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.about -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }
    /*fun nombreperfil(){
        val bundle = intent.extras
        if (bundle != null) {
            val nombre = bundle.getString("name")
            //val correo = bundle.getString("email")

            tv_nombreperfil.setText(nombre)
            //tv_nombreperfil.text = nombre
            //tv_email.setText(correo)
        }
    }*/
}