import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.andre.androidtoolbox.models.ContactModel
import fr.isen.andre.androidtoolbox.R
import kotlinx.android.synthetic.main.recycler_view_contact_cell.view.*

//Create a list of <ContactModel> inside the RecyclerView
class ContactsAdapter(private val contacts: ArrayList<ContactModel>): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        //Add one contact
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_contact_cell, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    //Create the contacts list
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    class ContactViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        //Attach current contact to the display list
        fun bind(contact: ContactModel) {
            view.contactDisplayNameTextView.text = contact.displayName
        }
    }
}