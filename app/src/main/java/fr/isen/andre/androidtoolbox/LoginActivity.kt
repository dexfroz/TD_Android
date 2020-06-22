package fr.isen.andre.androidtoolbox

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    //Correct login
    final private var IdentifiantFinal: String = "Admin";
    final private var MotDePasseFinal: String = "123";

    //Shared Preferences variables
    private val sharedPreferencesFile = "SavedDataPreferencesFile"
    private var sharedPreferences: SharedPreferences? = null

    //member variables
    private lateinit var mBouttonMontrerCacher: Button;
    private lateinit var mMotDePasse: EditText;
    private lateinit var mBouttonValider: Button;
    private lateinit var mIdentifiant: EditText;
    private lateinit var mIdentifiantText : TextView;

    //Test to know if correct login are input
    private var mBooleanIdentifiantEntrer: Boolean = false;
    private var mBooleanMotDePasseEntrer: Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init the SharedPreferences
        sharedPreferences = this.getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE)

        //Link members variables to xml views
        mBouttonMontrerCacher = findViewById<Button>(R.id.bouttonMontrerCacher);
        mMotDePasse = findViewById<EditText>(R.id.motDePasse);
        mBouttonValider = findViewById<Button>(R.id.bouttonValider);
        mIdentifiant = findViewById<EditText>(R.id.identifiant);
        mIdentifiantText = findViewById<TextView>(R.id.identifiantText);

        //default value for validate button
        mBouttonValider.isEnabled = false;

        //all functions managing xml views
        gestionBouttonMontrerCacher();
        gestionTextIdentifiant();
        gestionTextMotDePasse();
        gestionBouttonValider();
    }

    private fun gestionBouttonValider() {
        if(loadPreferences()){
            startHomeActivity()
        } else {
            mBouttonValider.setOnClickListener {
                //Save parameters and load new activity
                if (mBooleanIdentifiantEntrer && mBooleanMotDePasseEntrer) {
                    savePreferences()
                    startHomeActivity()
                } else {
                    Toast.makeText(this, "Accès refusé", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private fun loadPreferences(): Boolean {
        //check if saved parameters are good ones
        val id = sharedPreferences?.getString("id_key","defaultId")
        val password = sharedPreferences?.getString("password_key", "defaultPassword")
        if(IdentifiantFinal == id && MotDePasseFinal == password){
            return true
        }
        return false
    }

    private fun startHomeActivity() {
        //laod the HomeActivity and end the login
        Toast.makeText(this, "Accès autorisé", Toast.LENGTH_LONG).show();
        val homeActivity = Intent(this, HomeActivity::class.java);
        startActivity(homeActivity);
        finish();
    }


    private fun gestionTextMotDePasse() {
        mMotDePasse.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                mBouttonValider.isEnabled = (s.toString().length != 0);
                // This is where we'll check the user input
                mBooleanMotDePasseEntrer = (MotDePasseFinal.equals(s.toString()));
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun gestionTextIdentifiant() {
        mIdentifiant.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                //requires higher api : mIdentifiantText.setTextColor(resources.getColorStateList(R.color.red,null));
                mBouttonValider.isEnabled = (s.toString().length != 0);
                // This is where we'll check the user input
                mBooleanIdentifiantEntrer = (IdentifiantFinal.equals(s.toString()));
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }


    fun gestionBouttonMontrerCacher(){
        //manage the button Show/Hide password
        mBouttonMontrerCacher.setOnClickListener {
            if(mBouttonMontrerCacher.text.toString().equals("Montrer")){
                mMotDePasse.transformationMethod = HideReturnsTransformationMethod.getInstance()
                mBouttonMontrerCacher.text = "Cacher"
            } else{
                mMotDePasse.transformationMethod = PasswordTransformationMethod.getInstance()
                mBouttonMontrerCacher.text = "Montrer"
            }
        }
    }

    private fun savePreferences(){
        //Save data to automaticaly log the user
        val editor = sharedPreferences?.edit()
        editor?.putString("id_key",identifiant.text.toString())
        editor?.putString("password_key",motDePasse.text.toString())
        editor?.apply()
        editor?.commit()

        //Toast to display data saved
        //Toast.makeText(this, "Data saved: Id = "+ sharedPreferences?.getString("id_key","defaultId") + " MDP = "+sharedPreferences?.getString("password_key","defaultPassword"),Toast.LENGTH_SHORT).show()
    }
}
