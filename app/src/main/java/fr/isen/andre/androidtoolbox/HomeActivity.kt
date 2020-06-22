package fr.isen.andre.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    //Shared Preferences variables
    private val sharedPreferencesFile = "SavedDataPreferencesFile"
    private var sharedPreferences: SharedPreferences? = null

    //member variables
    private lateinit var mBouttonDeconnexion: Button
    private lateinit var mBouttonCycleDeVie: ImageButton
    private lateinit var mBouttonPermission: ImageButton
    private lateinit var mBouttonWebServices: ImageButton
    private lateinit var mBouttonSauvegarde: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Link members variables to xml views
        mBouttonDeconnexion = findViewById<Button>(R.id.deconnexionBouttonHomePage)
        mBouttonCycleDeVie = findViewById<ImageButton>(R.id.imageCycleDeVie)
        mBouttonPermission = findViewById<ImageButton>(R.id.imagePermissions)
        mBouttonWebServices = findViewById<ImageButton>(R.id.imageWebServices)
        mBouttonSauvegarde = findViewById<ImageButton>(R.id.imageSauvegarde)

        //all functions managing xml views
        gestionDeconnexionBoutton()
        gestionCycleDeVieBoutton()
        gestionPermissionBoutton()
        gestionWebServicesButton()
        gestionSauvegardeButton()
    }

    private fun gestionSauvegardeButton() {
        //Launch SaveActivity
        mBouttonSauvegarde.setOnClickListener{
            val sauvegardeActivity = Intent(this, SaveActivity::class.java)
            startActivity(sauvegardeActivity)
        }
    }

    private fun gestionWebServicesButton() {
        //Launch WebServicesActivity
        mBouttonWebServices.setOnClickListener{
            val webServicesActivity = Intent(this, WebServicesActivity::class.java)
            startActivity(webServicesActivity)
        }
    }

    private fun gestionPermissionBoutton() {
        //Launch PermissionActivity
        mBouttonPermission.setOnClickListener{
            val permissionActivity = Intent(this, PermissionActivity::class.java)
            startActivity(permissionActivity)
        }
    }

    private fun gestionCycleDeVieBoutton() {
        //Launch CycleDeVieActivity
        mBouttonCycleDeVie.setOnClickListener{
            val cycleDeVieActivity = Intent(this, CycleDeVieActivity::class.java);
            startActivity(cycleDeVieActivity);
        }
    }

    private fun gestionDeconnexionBoutton() {
        //Disconnect the user and launch LoginActivity
        mBouttonDeconnexion.setOnClickListener {
            deletePreferences()
            Toast.makeText(this, "Déconnexion",Toast.LENGTH_LONG).show();
            val loginActivity = Intent( this, LoginActivity::class.java);
            startActivity(loginActivity);
            finish();
        }
    }

    private fun deletePreferences() {
        //delete data saved in the sharedPreferencesFile
        sharedPreferences = getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()

        Toast.makeText(this,"Préférences Supprimées",Toast.LENGTH_SHORT).show()
    }
}
