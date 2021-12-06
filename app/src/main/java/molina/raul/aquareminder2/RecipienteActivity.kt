package molina.raul.aquareminder2

import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_recipiente.*
import molina.raul.aquareminder2.menu.MainActivity

class RecipienteActivity : AppCompatActivity() {
    var agua: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipiente)

        ib_vasomini.setOnClickListener {
            agua = 150
            tv_recipiente_cantidad.setText("$agua ml")
        }
        ib_vaso.setOnClickListener {
            agua = 250
            tv_recipiente_cantidad.setText("$agua ml")
        }
        ib_botella.setOnClickListener {
            agua = 600
            tv_recipiente_cantidad.setText("$agua ml")
        }
        ib_botellapersonal.setOnClickListener {
            agua = 1000
            tv_recipiente_cantidad.setText("$agua ml")
        }

        btn_recipiente_confirmar.setOnClickListener {
            if (agua != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("cantidad", agua)
                Toast.makeText(this, "Cantidad de $agua ml", Toast.LENGTH_SHORT).show()
            }

            /* when(agua!=null){
                 ib_vasomini.isSelected->{
                     val intent = Intent(this, MainActivity::class.java)
                     intent.putExtra("cantidad", 150)
                     Toast.makeText(this, "150 ml", Toast.LENGTH_SHORT).show()
                 }
                 ib_vaso.isSelected->{
                     val intent = Intent(this, MainActivity::class.java)
                     intent.putExtra("cantidad", 250)
                     Toast.makeText(this, "250 ml", Toast.LENGTH_SHORT).show()
                 }
                 ib_botella.isSelected->{
                     val intent = Intent(this, MainActivity::class.java)
                     intent.putExtra("cantidad", 600)
                     Toast.makeText(this, "600 ml", Toast.LENGTH_SHORT).show()
                 }
                 ib_botellapersonal.isSelected->{
                     val intent = Intent(this, MainActivity::class.java)
                     intent.putExtra("cantidad", 1000)
                     Toast.makeText(this, "1000 ml", Toast.LENGTH_SHORT).show()
                 }
             }*/
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPref2 = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        val editor = sharedPref2.edit()

        //agua = tv_recipiente_cantidad.text.toString()

        editor.putInt("aux", agua)
        editor.commit()
    }

    override fun onRestart() {
        super.onRestart()
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        agua = sharedPref.getInt("aux", 0)
        tv_recipiente_cantidad.setText("$agua")
    }
}