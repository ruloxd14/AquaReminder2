package molina.raul.aquareminder2.menu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.type.ColorOrBuilder
import com.jjoe64.graphview.series.DataPoint
import molina.raul.aquareminder2.Consumo
import molina.raul.aquareminder2.R
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.StaticLabelsFormatter






//import jdk.jfr.internal.handlers.EventHandler.timestamp







class DashboardActivity : AppCompatActivity() {
    //var fecha: Date = TODO()
    //var cantidad: Int = 0
    val date = Date()
    val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
    val fechaActual = dateFormat.format(date)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        menu()

        val fecha = FirebaseDatabase.getInstance().reference.child("Consumo").child("fecha")
        val cantidad = FirebaseDatabase.getInstance().reference.child("Consumo").child("cantidad")

        val entradas: ArrayList<Consumo> = ArrayList()
        //entradas.add(cantidad,fecha)
        //entradas.add(Consumo(500, fechaActual))
        /*entradas.add(Consumo(600, "2021/11/26"))
        entradas.add(Consumo(1000, "2021/11/27"))
        entradas.add(Consumo(1250, "2021/11/28"))
        entradas.add(Consumo(750, "2021/11/29"))
        entradas.add(Consumo(900, "2021/11/30"))*/

        //val dataset = BarDataSet(entradas, "ml de consumo")

        /*val etiquetas = ArrayList<String>()
        etiquetas.add("Enero")
        etiquetas.add("Febrero")
        etiquetas.add("Marzo")
        etiquetas.add("Abril")
        etiquetas.add("Mayo")
        etiquetas.add("Junio")*/

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                DataPoint(1.0, 1500.0),
                DataPoint(2.0, 2100.0),
                DataPoint(3.0, 3200.0),
                DataPoint(4.0, 1650.0),
                DataPoint(5.0, 1425.0),
                DataPoint(6.0, 1390.0),
                DataPoint(7.0, 1025.0),
                DataPoint(8.0, 975.0),
                DataPoint(9.0, 1280.0),
                DataPoint(10.0, 1350.0),
                DataPoint(11.0, 1500.0),
                DataPoint(12.0, 2100.0),
                DataPoint(13.0, 3200.0),
                DataPoint(14.0, 1650.0),
                DataPoint(15.0, 1425.0),
                DataPoint(16.0, 1390.0),
                DataPoint(17.0, 1025.0),
                DataPoint(18.0, 975.0),
                DataPoint(19.0, 1280.0),
                DataPoint(20.0, 1350.0)

            )
        )

        val gridLabel: GridLabelRenderer = graph.getGridLabelRenderer()
        gridLabel.horizontalAxisTitle = "Dias"
        gridLabel.verticalAxisTitle = "Consumo (ml)"
        series.setColor(Color.CYAN);
        /*val staticLabelsFormatter = StaticLabelsFormatter(graph)
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter)
        graph.getViewport().setScrollable(true)
        graph.getViewport().isScrollable()
        graph.getViewport().scrollToEnd()*/
        graph.getViewport().setScalable(true);  // activate horizontal zooming and scrolling
        graph.getViewport().setScrollable(true);  // activate horizontal scrolling
        graph.getViewport().setScalableY(true);  // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScrollableY(true);  // activate vertical scrolling
        graph.addSeries(series)

        Toast.makeText(this, "Grafica Scrollable", Toast.LENGTH_SHORT).show()

       /*val grafica = BarChart(applicationContext)
        setContentView(grafica)*/
    }

    fun menu(){
        //Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.botton_navigation)
        //set home
        bottomNavigationView.selectedItemId = (R.id.dashboard)

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.dashboard -> return@OnNavigationItemSelectedListener true
                R.id.home -> {
                    startActivity(
                        Intent(
                            applicationContext, MainActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
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


}