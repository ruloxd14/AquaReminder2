package molina.raul.aquareminder2.menu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_main.*
import molina.raul.aquareminder2.R
import android.annotation.SuppressLint
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import molina.raul.aquareminder2.Consumo
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    //private lateinit var database: DatabaseReference

    //var ISO_8601_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'")
    //var now = ISO_8601_FORMAT.format(Date())
    var cuenta: Int = 0
    var total: Int = 0
    var totalS: String? = "total"
    var agua: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu()

        /*val bundle = intent.extras
        if (bundle != null) {
            var valor = bundle.getInt("cantidad")
            aguasumatoria.setText("$valor / 2000 ml")
            cuenta = valor
            Toast.makeText(this, "$valor", Toast.LENGTH_SHORT).show()
        }*/
        //aguasumatoria.setText("0 / 2000 ml")

        iv_vaso_cantidad.setOnClickListener {
            val popMenu = PopupMenu(this, iv_vaso_cantidad)
            popMenu.menuInflater.inflate(R.menu.menu_recipiente, popMenu.menu)
            popMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {
                        R.id.menu_vaso_mini -> {
                            agua = 150
                            aguaselecionada.text = "$agua ml"
                        }
                        R.id.menu_vaso_normal -> {
                            agua = 250
                            aguaselecionada.text = "$agua ml"
                        }
                        R.id.menu_botella -> {
                            agua = 600
                            aguaselecionada.text = "$agua ml"
                        }
                        R.id.menu_botella_personal -> {
                            agua = 1000
                            aguaselecionada.text = "$agua ml"
                        }
                    }
                    return true
                }
            })
            //Toast.makeText(this, "Cantidad de $agua ml", Toast.LENGTH_SHORT).show()
            popMenu.show()
        }
        botonagregar.setOnClickListener {
            cuenta += agua
            //resta el excedente xd
            total = (cuenta + agua) - agua
            aguasumatoria.setText("$total / 2000 ml")
            storeDatetoFirebase()
        }
    }

    fun menu() {
        //Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botton_navigation)
        //set home
        bottomNavigationView.selectedItemId = (android.R.id.home)

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
                R.id.home -> return@OnNavigationItemSelectedListener true
                R.id.about -> {
                    startActivity(
                        Intent(
                            applicationContext, AboutActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

    override fun onPause() {
        super.onPause()
        val sharedPref2 = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        val editor = sharedPref2.edit()

        totalS = aguasumatoria.text.toString()

        editor.putInt("cuenta", cuenta)
        editor.putInt("total", total)
        editor.putInt("agua", agua)
        //reemplazando commit()
        editor.apply()

    }

    override fun onRestart() {
        super.onRestart()
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        cuenta = sharedPref.getInt("cuenta", 0)
        total = sharedPref.getInt("total", 0)
        agua = sharedPref.getInt("agua", 0)
        aguasumatoria.setText("$total")
        aguaselecionada.setText("$agua")
    }

    fun storeDatetoFirebase() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                handler
                handler.postDelayed(this, 1000)
                try {
                    val date = Date()
                   // val newDate = Date(date.getTime() + 604800000L * 2 + 24 * 60 * 60)
                    //val dt = SimpleDateFormat("yyyy-MM-dd")
                    //val stringdate: String = dt.format(newDate)
                    //println("Submission Date: $stringdate")

                    val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
                    val strDate: String = dateFormat.format(date).toString()

                    val databaseReference: DatabaseReference =
                        FirebaseDatabase.getInstance().getReference().child("Consumo")
                    databaseReference.child("cantidad").setValue("$total")
                    databaseReference.child("fecha").setValue(strDate)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)
    }

}