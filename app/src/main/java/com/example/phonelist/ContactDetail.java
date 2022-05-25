package com.example.phonelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.phonelist.models.Contact;


public class ContactDetail extends AppCompatActivity {

    private Integer contactId = -1;
    private Integer contactIndex = -1;
    private ImageButton saveButton, exitButton;
    private EditText editTextName, editTextAddress, editTextTelephone, editTextCellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setInitWidgets();
        setIntentValuesIfExist();
        exitButtonOnClick();
        saveButtonClick();
    }

    private void setIntentValuesIfExist() {
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            contactIndex = intent.getIntExtra("index", -1);
            contactId = intent.getIntExtra("id", - 1);
            editTextName.setText(intent.getStringExtra("name"));
            editTextAddress.setText(intent.getStringExtra("address"));
            editTextTelephone.setText(intent.getStringExtra("telephone"));
            editTextCellphone.setText(intent.getStringExtra("cellphone"));
        }
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
            String operation = contactId != -1 ? SqlMethods.UPDATE.name() : SqlMethods.INSERT.name();
            Integer index = contactIndex != -1 ? contactIndex : Contact.contacts.size() - 1;

            handleOperation(index, operation);

            Intent intent = new Intent();
            intent.putExtra("index", index);
            intent.putExtra("operation", operation);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void handleOperation(int index, String operation) {
        Integer id = contactId != -1 ? contactId : Contact.contacts.size();
        String name = String.valueOf(editTextName.getText());
        String address = String.valueOf(editTextAddress.getText());
        String telephone = String.valueOf(editTextTelephone.getText());
        String cellphone = String.valueOf(editTextCellphone.getText());

        Contact contact = new Contact(id, name, address, telephone, cellphone);

        if (operation.equals(SqlMethods.INSERT.name())) Contact.contacts.add(contact);
        else Contact.contacts.set(index, contact);
    }

}