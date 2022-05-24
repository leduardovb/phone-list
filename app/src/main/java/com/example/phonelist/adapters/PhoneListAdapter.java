package com.example.phonelist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.phonelist.R;
import com.example.phonelist.models.Contact;

import java.util.ArrayList;

public class PhoneListAdapter extends ArrayAdapter<Contact> {

    private Contact contact;
    private TextView textName, textAddress, textTelephone, textCellphone;

    public PhoneListAdapter(@NonNull Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.contact = getItem(position);
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        setInitWidgets(convertView);
        setTextInInitWidgets(this.contact);

        return convertView;
    }

    private void setInitWidgets(View view) {
       textName = view.findViewById(R.id.name);
       textAddress = view.findViewById(R.id.address);
       textTelephone = view.findViewById(R.id.telephone);
       textCellphone = view.findViewById(R.id.cellphone);
    }

    private void setTextInInitWidgets(Contact contact) {
        textName.setText(contact.getName());
        textAddress.setText(contact.getAddress());
        textTelephone.setText(contact.getTelephone());
        textCellphone.setText(contact.getCellphone());
    }

    private Contact getContact() {
        return this.contact;
    }

}
