package molina.raul.aquareminder2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inicio_sesion.*
import molina.raul.aquareminder2.menu.AboutActivity
import molina.raul.aquareminder2.menu.MainActivity

class InicioSesionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient

    //val botongoogle: Button = findViewById(R.id.sign_in_button)
    val RC_SIGN_IN = 123
    val COD_LOGOUT = 321

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val botoniniciosesion = findViewById<Button>(R.id.buttoniniciosesion)
        val botonolvidar = findViewById<TextView>(R.id.olvidar)
        val botonregistrar = findViewById<Button>(R.id.registar)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        botoniniciosesion.setOnClickListener {
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
            valida_ingreso()
        }
        botonolvidar.setOnClickListener {
            val intent = Intent(this, RecuperarActivity::class.java)
            startActivity(intent)
        }
        botonregistrar.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }

    }

    private fun valida_ingreso() {
        var correo: String = et_correo.text.toString()
        var contra: String = et_contrasena.text.toString()

        if (!correo.isNullOrBlank() && !contra.isNullOrBlank()) {
            ingresaFirebase(correo, contra)
        } else {
            Toast.makeText(
                this, "Faltan datos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun ingresaFirebase(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Error de ingreso",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        if (requestCode == COD_LOGOUT) {
            signOut()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                Toast.makeText(this, "Sesion Terminada", Toast.LENGTH_SHORT).show()
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", account.displayName)
            intent.putExtra("email", account.email)
            intent.putExtra("photo", account.photoUrl)
            startActivityForResult(intent, COD_LOGOUT)
        }
    }


    //Puente entre actividad y fragmento
    //no funciona
    /**
    fun newInstance(account: GoogleSignInAccount?): NotificationsFragment {
    val f = NotificationsFragment()
    // Supply index input as an argument.
    val args = Bundle()
    if (account != null) {
    args.putString("name", account.displayName)
    args.putString("photo", account.photoUrl?.toString())
    f.setArguments(args)
    //f.arguments = args
    }
    return f
    }
     */
}