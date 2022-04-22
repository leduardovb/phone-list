package com.example.phonelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PhoneListAdapter(
    private val myContext: Context,
    private val resource: Int,
    private val contacts: List<Contact>
): ArrayAdapter<Contact>(myContext, resource, contacts) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context) as LayoutInflater
        val view: View = inflater.inflate(R.layout.list_item, null)

        val textName: TextView = view.findViewById(R.id.name)
        val textPhone: TextView = view.findViewById(R.id.cellphone)
        val textAddress: TextView = view.findViewById(R.id.address)
        val textObservation: TextView = view.findViewById(R.id.observation)

        val contact: Contact = contacts[position]

        textName.text = contact.name
        textPhone.text = contact.phone
        textAddress.text = contact.streetName
        textObservation.text = contact.observation

        return view
    }
}