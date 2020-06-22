package fr.isen.andre.androidtoolbox

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.andre.androidtoolbox.adapter.UserModelAdaptater
import fr.isen.andre.androidtoolbox.models.RandomUserResult
import kotlinx.android.synthetic.main.activity_web_services.*

class WebServicesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)

        launchService()
    }

    private fun launchService() {
        // Launch the discussion with an API that create random users and display them on the view

        val userApiUrl = "https://randomuser.me/api/?results=20"
        val queue = Volley.newRequestQueue(this)

        val req = StringRequest(Request.Method.GET, userApiUrl, Response.Listener {

            val gson = Gson()
            val sortie = gson.fromJson(it, RandomUserResult::class.java)

            recycleViewUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            sortie.results?.let {
                recycleViewUser.adapter = UserModelAdaptater(it)
            }


        }, Response.ErrorListener {
            Log.d("volley", it.toString())
        })





        queue.add(req)

    }
}
