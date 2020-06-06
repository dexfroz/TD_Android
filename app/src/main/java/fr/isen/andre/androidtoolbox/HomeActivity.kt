package fr.isen.andre.androidtoolbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {


    private lateinit var mBouttonDeconnexion: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mBouttonDeconnexion = findViewById<Button>(R.id.deconnexionBouttonHomePage)

        gestionDeconnexionBoutton();
    }

    private fun gestionDeconnexionBoutton() {
        mBouttonDeconnexion.setOnClickListener {
            Toast.makeText(this, "Déconnexion",Toast.LENGTH_LONG).show();
            val mainActivity = Intent( this, MainActivity::class.java);
            startActivity(mainActivity);
        }
    }
}
