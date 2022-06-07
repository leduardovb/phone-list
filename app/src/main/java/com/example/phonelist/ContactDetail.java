package com.example.phonelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.phonelist.models.Contact;
import com.example.phonelist.models.Toast;
import com.example.phonelist.services.ContactService;


public class ContactDetail extends AppCompatActivity {

    private Integer contactId = -1;
    private Integer contactIndex = -1;
    private ImageButton saveButton, exitButton;
    private EditText editTextName, editTextAddress, editTextTelephone, editTextCellphone;
    private final ContactService contactService = new ContactService(this);
    private final Toast toast = new Toast(this);

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
            String operation = contactId != -1 ? SQL_OPERATIONS.UPDATE.name() : SQL_OPERATIONS.INSERT.name();

            if (handleOperation(operation)) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean handleOperation(String operation) {
        Contact contact = getContactData();
        String message = "Favor preencher todos os campos!";
        boolean status = false;

        if (validateContactFields(contact)) {
            if (operation.equals(SQL_OPERATIONS.INSERT.name())) {
                int savedId = contactService.newContact(contact);
                if (savedId != -1) {
                    status = true;
                    contact.setId(savedId);
                    Contact.contacts.add(contact);
                    message = "Contato inserido com sucesso";
                } else message = "Erro ao inserir o contato!";
            } else {
                if (contactService.updateContact(contact)) {
                    status = true;
                    Contact.contacts.set(contactIndex, contact);
                    message = "Contato atualizado com sucesso";
                }
                else message = "Erro ao atualizar o contato";
            };
        }

        toast.showLongMessage(message);
        return status;
    }

    private Contact getContactData() {
        Integer id = contactId != -1 ? contactId : Contact.contacts.size();
        String name = String.valueOf(editTextName.getText());
        String address = String.valueOf(editTextAddress.getText());
        String telephone = String.valueOf(editTextTelephone.getText());
        String cellphone = String.valueOf(editTextCellphone.getText());

        return new Contact(id, name, address, telephone, cellphone);
    }

    private boolean validateContactFields(Contact contact) {
        boolean isValidated = true;
        if (contact.getName().length() == 0) isValidated = false;
        else if (contact.getAddress().length() == 0) isValidated = false;
        else if (contact.getTelephone().length() == 0) isValidated = false;
        else if (contact.getCellphone().length() == 0) isValidated = false;
        return isValidated;
    }

}