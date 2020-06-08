package fr.isen.andre.androidtoolbox

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class CycleFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cycle, container, false)
    }

    override fun onPause() {
        super.onPause()
        Log.i("CycleFragment", "Fragment en Pause")
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        Toast.makeText(context, "Fragment détruit", Toast.LENGTH_LONG).show()
    }
}