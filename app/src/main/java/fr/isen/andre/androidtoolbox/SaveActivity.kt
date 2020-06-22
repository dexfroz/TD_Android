package fr.isen.andre.androidtoolbox

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.andre.androidtoolbox.R.id
import fr.isen.andre.androidtoolbox.R.layout
import kotlinx.android.synthetic.main.activity_save.*
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SaveActivity : AppCompatActivity() {

    companion object {
        const val firstName = "Prénom: "
        const val lastName = "Nom: "
        const val birthDay = "Date Naissance: "
        const val FileName = "UserData.json"
    }

    //current date variables
    private var currentYear: Int = 0
    private var currentMonth: Int = 0
    private var currentDay: Int = 0

    //member variables
    private lateinit var mBouttonEnvoyer: Button
    private lateinit var mBouttonVoir: Button
    private lateinit var mNomPersonne: EditText
    private lateinit var mPrenomPersonne: EditText
    private lateinit var mDateNaissancePersonne: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_save)

        //Link members variables to xml views
        mBouttonEnvoyer = findViewById(id.bouttonEnvoyerFormulaire)
        mBouttonVoir = findViewById(id.bouttonVoirFormulaire)
        mNomPersonne = findViewById(id.nomPersonneFormulaire)
        mPrenomPersonne = findViewById(id.prenomPersonneFormulaire)
        mDateNaissancePersonne = findViewById(id.dateNaissancePersonneFormulaire)

        //all functions managing xml views
        initDate()
        gestionBouttonEnvoyer()
        gestionBouttonVoir()
        gestionDateNaissance()
    }

    private fun initDate() {
        //initialize the current date and save each part to date variables
        var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val currentDateString = formatter.format(currentDate)
        val components = currentDateString.split("/")
        currentYear = components[2].toInt()
        currentMonth = components[1].toInt()
        currentDay = components[0].toInt()
    }

    private fun gestionDateNaissance() {
        //display a dialog box to select the birthday of user
        mDateNaissancePersonne.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                mDateNaissancePersonne.clearFocus()
                val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    dateNaissancePersonneFormulaire.setText(String.format("%02d/%02d/%04d",dayOfMonth,month+1,year))
                },currentYear,currentMonth-1,currentDay)

                dialog.show()
                dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }
        }
    }

    private fun gestionBouttonVoir() {
        //See users data saved by clicking
        mBouttonVoir.setOnClickListener {
            view()
        }
    }

    private fun view() {
        //Calculate the age of the user and display all data saved in the file + age
        val file = File(cacheDir.absolutePath+"/"+FileName)
        val string = file.readText()

        val json = JSONObject(string)
        val age = calculAge(json.getString(birthDay))
        json.put("Age: ", age.toString())
        Toast.makeText(this,json.toString(),Toast.LENGTH_LONG).show()
    }

    private fun gestionBouttonEnvoyer() {
        //send data to the file
        mBouttonEnvoyer.setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        //save all data in the local file if all field are completed.
        if(mPrenomPersonne.text.toString().isNotEmpty() &&
                mNomPersonne.text.toString().isNotEmpty() &&
                mDateNaissancePersonne.text.toString().isNotEmpty()){

            val json = JSONObject()
            val file = File(cacheDir.absolutePath+"/"+ FileName)
            json.put(firstName, mPrenomPersonne.text.toString())
            json.put(lastName, mNomPersonne.text.toString())
            json.put(birthDay, mDateNaissancePersonne.text.toString())

            Toast.makeText(this, json.toString(), Toast.LENGTH_SHORT).show()
            file.writeText(json.toString())
        } else {
            Toast.makeText(this, "Erreur champs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculAge(date: String): Int {
        //calculate the age depending on input String with the format (DD/MM/AAAA)
        val dayBirth = date.substring(0,2).toInt()
        val birthMonth = date.substring(3,5).toInt()
        val birthYear = date.substring(6,10).toInt()

        var age: Int = currentYear - birthYear
        if(currentMonth - birthMonth < 0) {
            age--
        } else if(currentMonth - birthMonth == 0){
            if(currentDay - dayBirth < 0){
                age--
            }
        }

        return age
    }
}
