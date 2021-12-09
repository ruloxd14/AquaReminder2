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
import android.util.Log
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
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    //private lateinit var database: DatabaseReference

    //var ISO_8601_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'")
    //var now = ISO_8601_FORMAT.format(Date())
    var cuenta: Int = 0
    var total: Int = 0
    var recipiente: String? = ""
    var agua: Int = 0
    var cantidad: String = ""
    //var cantidadint: Int = 0

    var fdb = FirebaseDatabase.getInstance().getReference()

    val date = Date()
    val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
    val dateFormatDay: DateFormat = SimpleDateFormat("dd")
    val diaActual = dateFormatDay.format(date)
    val fechaActual = dateFormat.format(date)
    val stringFechaActual: String = diaActual
    val path: String = "Consumo " + stringFechaActual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu()
        actualizarConsumo()

        Toast.makeText(this, "Bienvenido ✌✌", Toast.LENGTH_SHORT).show()

        iv_vaso_cantidad.setOnClickListener {
            val popMenu = PopupMenu(this, iv_vaso_cantidad)
            popMenu.menuInflater.inflate(R.menu.menu_recipiente, popMenu.menu)
            popMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item!!.itemId) {
                        R.id.menu_vaso_mini -> {
                            agua = 150
                            aguaselecionada.text = "$agua ml"
                            actualizarRecipiente()
                        }
                        R.id.menu_vaso_normal -> {
                            agua = 250
                            aguaselecionada.text = "$agua ml"
                            actualizarRecipiente()
                        }
                        R.id.menu_botella -> {
                            agua = 600
                            aguaselecionada.text = "$agua ml"
                            actualizarRecipiente()
                        }
                        R.id.menu_botella_personal -> {
                            agua = 1000
                            aguaselecionada.text = "$agua ml"
                            actualizarRecipiente()
                        }
                    }
                    return true
                }
            })
            //Toast.makeText(this, "Cantidad de $agua ml", Toast.LENGTH_SHORT).show()
            popMenu.show()
        }
        botonagregar.setOnClickListener {
            var cantidadint = Integer.parseInt(cantidad)
            var recipienteint = Integer.parseInt(recipiente)
            //cuenta += agua
            cantidadint += recipienteint
            //resta el excedente xd
            total = (cantidadint + recipienteint) - recipienteint
            aguasumatoria.setText("$total / 3700 ml")
            actualizarVaso()
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

        recipiente = aguaselecionada.text.toString()

        editor.putInt("agua", agua)
        //editor.putInt("total", total)
        //editor.putInt("agua", agua)
        //reemplazando commit()
        editor.apply()

    }

    override fun onRestart() {
        super.onRestart()
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        //cuenta = sharedPref.getInt("cuenta", 0)
        //total = sharedPref.getInt("total", 0)
        agua = sharedPref.getInt("agua", 0)
        //aguasumatoria.setText("$total")
        aguaselecionada.setText("$agua")
    }

    fun storeDatetoFirebase() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                handler
                handler.postDelayed(this, 1000)
                try {
                    /* val newDate = Date(date.getTime() + 604800000L * 2 + 24 * 60 * 60)
                     //val dt = SimpleDateFormat("yyyy-MM-dd")
                     //val stringdate: String = dt.format(newDate)
                     //println("Submission Date: $stringdate")
                     //val calendar = Calendar.getInstance()
                     //calendar.setTime(date)
                     //val dia: String = calendar.add(Calendar.DAY_OF_YEAR, 1).toString()
                     var aux : Int = 0*/

                    //obtencion de la fecha de Firebase
                    val fechaCom =
                        FirebaseDatabase.getInstance().reference.child("Consumo").child("fecha")
                    //comparacion entre la fecha que guardada con la fecha actual
                    if (fechaCom == dateFormat) {
                        //si es el mismo dia solo actualiza la cantidad
                        ActualizarFirebaseDiaActual()
                    } else {
                        //si la fecha es diferente la tabla guardara un nuevo dia
                        val databaseReference: DatabaseReference =
                            FirebaseDatabase.getInstance().getReference()
                                .child("Consumo " + "$stringFechaActual")
                        databaseReference.child("cantidad").setValue("$total")
                        databaseReference.child("fecha").setValue(fechaActual)
                        //databaseReference.child("recipiente").setValue(agua)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler.postDelayed(runnable, 1 * 1000)
    }

    fun ActualizarFirebaseDiaActual() {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference().child("Consumo ")
        databaseReference.child("cantidad").setValue("$total")
    }

    fun actualizarConsumo() {
        fdb.child(path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //val canti = snapshot.child("cantidad").value.toString()
                    cantidad = snapshot.child("cantidad").value.toString()
                    recipiente = snapshot.child("recipiente").value.toString()
                    aguaselecionada.setText(recipiente + " ml")
                    aguasumatoria.setText(cantidad + " / 3700 ml")

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }

    fun actualizarVaso() {
        if (total >= 528.58) {
            iv_vaso_cantidad.setImageResource(R.drawable.v1)
            Toast.makeText(this, "Muy bien", Toast.LENGTH_SHORT).show()
        }
        if (total >= 1057.16) {
            iv_vaso_cantidad.setImageResource(R.drawable.v2)
            Toast.makeText(this, "Sigue así", Toast.LENGTH_SHORT).show()
        }
        if (total >= 1585.74) {
            iv_vaso_cantidad.setImageResource(R.drawable.v3)
            Toast.makeText(this, "Vamos", Toast.LENGTH_SHORT).show()
        }
        if (total >= 2114.32) {
            iv_vaso_cantidad.setImageResource(R.drawable.v4)
            Toast.makeText(this, "Sigue así", Toast.LENGTH_SHORT).show()
        }
        if (total >= 2642.9) {
            iv_vaso_cantidad.setImageResource(R.drawable.v5)
            Toast.makeText(this, "Muy bien", Toast.LENGTH_SHORT).show()
        }
        if (total >= 3171.48) {
            iv_vaso_cantidad.setImageResource(R.drawable.v6)
            Toast.makeText(this, "Un poco mas", Toast.LENGTH_SHORT).show()
        }
        if (total >= 3550) {
            iv_vaso_cantidad.setImageResource(R.drawable.v7)
            Toast.makeText(this, "Ya casi", Toast.LENGTH_SHORT).show()
        }
        if (total >= 3700) {
            iv_vaso_cantidad.setImageResource(R.drawable.vll)
            Toast.makeText(this, "Excelente", Toast.LENGTH_SHORT).show()
        }
        if (total >= 4000) {
            Toast.makeText(this, "Cuidado", Toast.LENGTH_SHORT).show()
        }
        /*when(total >= 0) {
            total >= 528.58 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v1)
                Toast.makeText(this, "Muy bien", Toast.LENGTH_SHORT).show()
            }
            total >= 1057.16 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v2)
                Toast.makeText(this, "Sigue así", Toast.LENGTH_SHORT).show()
            }
            total >= 1585.74 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v3)
                Toast.makeText(this, "Vamos", Toast.LENGTH_SHORT).show()
            }
            total >= 2114.32 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v4)
                Toast.makeText(this, "Sigue así", Toast.LENGTH_SHORT).show()
            }
            total >= 2642.9 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v5)
                Toast.makeText(this, "Muy bien", Toast.LENGTH_SHORT).show()
            }
            total >= 3171.48 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v6)
                Toast.makeText(this, "Un poco mas", Toast.LENGTH_SHORT).show()
            }
            total >= 3550 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.v7)
                Toast.makeText(this, "Ya casi", Toast.LENGTH_SHORT).show()
            }
            total == 3700 -> {
                iv_vaso_cantidad.setImageResource(R.drawable.vll)
                Toast.makeText(this, "Excelente", Toast.LENGTH_SHORT).show()
            }
            total > 4000 -> {
                Toast.makeText(this, "Cuidado", Toast.LENGTH_SHORT).show()
            }
        }*/

    }

    fun actualizarRecipiente(){
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference()
                .child("Consumo " + "$stringFechaActual")
        databaseReference.child("recipiente").setValue(agua)
    }

}