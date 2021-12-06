package molina.raul.aquareminder2.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jjoe64.graphview.series.DataPoint
import molina.raul.aquareminder2.Consumo
import molina.raul.aquareminder2.R
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        menu()

        val entradas: ArrayList<Consumo> = ArrayList()
        /*entradas.add(Consumo(500, "2021/11/25"))
        entradas.add(Consumo(600, "2021/11/26"))
        entradas.add(Consumo(1000, "2021/11/27"))
        entradas.add(Consumo(1250, "2021/11/28"))
        entradas.add(Consumo(750, "2021/11/29"))
        entradas.add(Consumo(900, "2021/11/30"))*/

        //val dataset = BarDataSet(entradas, "ml de consumo")

        val etiquetas = ArrayList<String>()
        etiquetas.add("Enero")
        etiquetas.add("Febrero")
        etiquetas.add("Marzo")
        etiquetas.add("Abril")
        etiquetas.add("Mayo")
        etiquetas.add("Junio")

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 3.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 6.0)
            )
        )
        graph.addSeries(series);


       // val grafica = BarChart(applicationContext)
        //setContentView(grafica)

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