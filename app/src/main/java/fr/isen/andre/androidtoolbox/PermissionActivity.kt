package fr.isen.andre.androidtoolbox


import ContactsAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.andre.androidtoolbox.models.ContactModel
import kotlinx.android.synthetic.main.activity_permission.*


class PermissionActivity : AppCompatActivity(), LocationListener {



    lateinit var locationManager: LocationManager

    private lateinit var mImagePermissionActivity: ImageButton
    private lateinit var mLocationTextView : TextView

    companion object {
        const val pictureRequestCode = 1
        const val contactPermissionRequestCode = 2
        const val gpsPermissionRequestCode = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        mImagePermissionActivity = findViewById<ImageButton>(R.id.imagePermissionActivity)
        mLocationTextView = findViewById<TextView>(R.id.locationTextView)

        gestionImagePermissionActivity();

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        requestPermission(android.Manifest.permission.READ_CONTACTS, contactPermissionRequestCode) {
            readContacts()
            Toast.makeText(this, "Accès contact autorisé", Toast.LENGTH_SHORT).show()
        }
        requestPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, gpsPermissionRequestCode) {
            startGPS()

        }
    }


    private fun gestionImagePermissionActivity(){
       mImagePermissionActivity.setOnClickListener{
           pictureLecture()
       }
   }

    private fun pictureLecture() {
         var pictureIntent = Intent(Intent.ACTION_PICK)
        pictureIntent.type = "image/*"

         var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

         var intentChooser = Intent.createChooser(pictureIntent, "gallery photo")

         intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
         startActivityForResult(intentChooser, PermissionActivity.pictureRequestCode)
     }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PermissionActivity.pictureRequestCode && resultCode == Activity.RESULT_OK) {
            if (data?.data != null) {
                mImagePermissionActivity.setImageURI(data.data)
            } else {
                val bitmap = data?.extras?.get("data") as? Bitmap
                bitmap?.let {
                    mImagePermissionActivity.setImageBitmap(it)
                }
            }
        }
    }

    fun refreshPosition(location: Location) {
        mLocationTextView.text = "latitude : ${location.latitude} \nlongitude : ${location.longitude}"
        Toast.makeText(this, "latitude : ${location.latitude} \nlongitude : ${location.longitude}", Toast.LENGTH_LONG).show()
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            refreshPosition(it)
        }
        Log.d("gps","maj position")
    }

    private fun readContacts() {
        val contactList = ArrayList<ContactModel>()
        val contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while(contacts?.moveToNext() == true) {
            val displayName = contacts.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val contactModel = ContactModel()
            contactModel.displayName = displayName.toString()
            contactList.add(contactModel)
        }
        contactRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contactRecyclerView.adapter = ContactsAdapter(contactList)
    }

    fun requestPermission(permissionToRequest: String, requestCode: Int, handler: ()-> Unit) {
        if(ContextCompat.checkSelfPermission(this, permissionToRequest) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissionToRequest)) {
                //display toast
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permissionToRequest), requestCode)
            }
        } else {
            handler()
        }
    }

    @SuppressLint("MissingPermission")
    fun startGPS(){
        Log.d("gps","start position")
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,this,null)
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let{
            onLocationChanged(it)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isNotEmpty()) {
            if (requestCode == contactPermissionRequestCode &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                readContacts()
            }
            else if(requestCode == gpsPermissionRequestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startGPS()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
