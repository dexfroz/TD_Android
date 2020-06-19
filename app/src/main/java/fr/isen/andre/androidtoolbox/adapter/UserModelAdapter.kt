package fr.isen.andre.androidtoolbox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.andre.androidtoolbox.R
import fr.isen.andre.androidtoolbox.models.UserModel


import kotlinx.android.synthetic.main.recycler_view_user_cell.view.*

class UserModelAdaptater(val contacts: ArrayList<UserModel>): RecyclerView.Adapter<UserModelAdaptater.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_user_cell, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
        holder.picasso(contact)
    }

    class ContactViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(contact: UserModel) {

            var nom = contact.name?.first + " " + contact.name?.last + "\n"
            var adresse = contact.location?.street?.number.toString() + " " + contact.location?.street?.name + ", " + contact.location?.city + ", " + contact.location?.country
            view.nomText.text = nom
            view.adressText.text = adresse
        }

        fun picasso(contact: UserModel){
            Picasso.get().load(contact.picture?.thumbnail).resize(200, 200).into(view.pictureUser)
        }
    }
}