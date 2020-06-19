package fr.isen.andre.androidtoolbox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {


    private lateinit var mBouttonDeconnexion: Button;
    private lateinit var mBouttonCycleDeVie: ImageButton
    private lateinit var mBouttonPermission: ImageButton
    private lateinit var mBouttonWebServices: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mBouttonDeconnexion = findViewById<Button>(R.id.deconnexionBouttonHomePage)
        mBouttonCycleDeVie = findViewById<ImageButton>(R.id.imageCycleDeVie)
        mBouttonPermission = findViewById<ImageButton>(R.id.imagePermissions)
        mBouttonWebServices = findViewById<ImageButton>(R.id.imageWebServices)

        gestionDeconnexionBoutton();
        gestionCycleDeVieBoutton();
        gestionPermissionBoutton();
        gestionWebServicesButton();
    }

    private fun gestionWebServicesButton() {
        mBouttonWebServices.setOnClickListener{
            val webServicesActivity = Intent(this, WebServicesActivity::class.java)
            startActivity(webServicesActivity)
        }
    }

    private fun gestionPermissionBoutton() {
        mBouttonPermission.setOnClickListener{
            val permissionActivity = Intent(this, PermissionActivity::class.java)
            startActivity(permissionActivity)
        }
    }

    private fun gestionCycleDeVieBoutton() {
        mBouttonCycleDeVie.setOnClickListener{
            val cycleDeVieActivity = Intent(this, CycleDeVieActivity::class.java);
            startActivity(cycleDeVieActivity);
        }
    }

    private fun gestionDeconnexionBoutton() {
        mBouttonDeconnexion.setOnClickListener {
            Toast.makeText(this, "Déconnexion",Toast.LENGTH_LONG).show();
            val loginActivity = Intent( this, LoginActivity::class.java);
            startActivity(loginActivity);
            finish();
        }
    }
}
