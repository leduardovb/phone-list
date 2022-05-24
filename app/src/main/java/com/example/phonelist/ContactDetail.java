package com.example.phonelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.phonelist.models.Contact;


public class ContactDetail extends AppCompatActivity {

    private ImageButton saveButton, exitButton;
    private EditText editTextName, editTextAddress, editTextTelephone, editTextCellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setInitWidgets();
        exitButtonOnClick();
        saveButtonClick();
    }

    private void setInitWidgets() {
        saveButton = findViewById(R.id.saveButton);
        exitButton = findViewById(R.id.cancelButton);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextTelephone = findViewById(R.id.textEditTelephone);
        editTextCellphone = findViewById(R.id.editTextCellphone);
    }

    private void exitButtonOnClick() {
        exitButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

    public void saveButtonClick() {
        saveButton.setOnClickListener(view -> {
            String name = String.valueOf(editTextName.getText());
            String address = String.valueOf(editTextAddress.getText());
            String telephone = String.valueOf(editTextTelephone.getText());
            String cellphone = String.valueOf(editTextCellphone.getText());

            Integer id = Contact.contacts.size();
            Contact contact = new Contact(id, name, address, telephone, cellphone);
            Contact.contacts.add(contact);

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }

}