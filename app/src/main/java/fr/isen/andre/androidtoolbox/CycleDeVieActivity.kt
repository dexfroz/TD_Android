package fr.isen.andre.androidtoolbox

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CycleDeVieActivity : AppCompatActivity() {

    //member variable
    private lateinit var mTextCycleDeVieActivity: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle_de_vie)

        //Link members variables to xml views
        mTextCycleDeVieActivity = findViewById<TextView>(R.id.textCycleDeVieActivity)
    }

    override fun onResume() {
        super.onResume()
        mTextCycleDeVieActivity.text = "Activité en vie"
    }

    override fun onPause() {
        super.onPause()
        Log.i("CycleDeVieActivity", "Activité en pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Activité Cycle de vie détruite", Toast.LENGTH_LONG).show()
    }
}
