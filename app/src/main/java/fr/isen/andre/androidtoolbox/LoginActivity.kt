package fr.isen.andre.androidtoolbox

import android.content.Intent
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

class LoginActivity : AppCompatActivity() {

    final private var IdentifiantFinal: String = "Admin";
    final private var MotDePasseFinal: String = "123";

    private lateinit var mBouttonMontrerCacher: Button;
    private lateinit var mMotDePasse: EditText;
    private lateinit var mBouttonValider: Button;
    private lateinit var mIdentifiant: EditText;
    private lateinit var mIdentifiantText : TextView;

    private var mBooleanIdentifiantEntrer: Boolean = false;
    private var mBooleanMotDePasseEntrer: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mBouttonMontrerCacher = findViewById<Button>(R.id.bouttonMontrerCacher);
        mMotDePasse = findViewById<EditText>(R.id.motDePasse);
        mBouttonValider = findViewById<Button>(R.id.bouttonValider);
        mIdentifiant = findViewById<EditText>(R.id.identifiant);
        mIdentifiantText = findViewById<TextView>(R.id.identifiantText);

        mBouttonValider.isEnabled = false;

        gestionBouttonMontrerCacher();
        gestionTextIdentifiant();
        gestionTextMotDePasse();
        gestionBouttonValider();
        /*


        mBouttonValider.isEnabled = false;




*/
    }

    private fun gestionBouttonValider() {
        mBouttonValider.setOnClickListener {
            //Display toast
            if(mBooleanIdentifiantEntrer && mBooleanMotDePasseEntrer) {
                Toast.makeText(this, "Accès autorisé", Toast.LENGTH_LONG).show();
                val homeActivity = Intent(this, HomeActivity::class.java);
                startActivity(homeActivity);
                finish();
            }
            else{
                Toast.makeText(this, "Accès refusé", Toast.LENGTH_LONG).show();
            }
        }
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
        //gestion boutton Montrer/Cacher
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
}
