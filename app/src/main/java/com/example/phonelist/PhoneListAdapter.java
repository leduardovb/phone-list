package com.example.phonelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PhoneListAdapter extends ArrayAdapter<Contact> {

    private TextView textName, textAddress, textTelephone, textCellphone;

    public PhoneListAdapter(@NonNull Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contact = getItem(position);
        if (convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        setInitWidgets(convertView);
        setTextInInitWidgets(contact);

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

}
