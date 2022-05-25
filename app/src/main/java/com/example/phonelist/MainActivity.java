package com.example.phonelist;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.phonelist.adapters.PhoneListAdapter;
import com.example.phonelist.database.SQLiteManager;
import com.example.phonelist.models.Contact;
import com.example.phonelist.services.ContactService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private PhoneListAdapter phoneListAdapter;
    private ActivityResultLauncher<Intent> register;
    private final ContactService contactService = new ContactService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        onResult();
        setFloatingButton();
        getContactListFromDatabase();
        setPhoneListAdapter();
    }

    private void initWidgets() {
        listView = findViewById(R.id.contacts);
    }

    private void onResult() {
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent != null && intent.hasExtra("operation")) {
                    int contactIndex = intent.getIntExtra("index", -1);
                    String operation = intent.getStringExtra("operation");
                    if (contactIndex != - 1) {
                        Contact savedContact = Contact.contacts.get(contactIndex);
                        if (operation.equals(SqlMethods.INSERT.name())) {
                            int savedId = contactService.newContact(savedContact);
                            if (savedId != -1) {
                                savedContact.setId(savedId);
                                Contact.updateContactByIndex(contactIndex, savedContact);
                            } else Contact.contacts.remove(contactIndex);
                        } else contactService.updateContact(Contact.contacts.get(contactIndex));
                    }
                    phoneListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setFloatingButton() {
        FloatingActionButton addContactButton = findViewById(R.id.add);
        addContactButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactDetail.class);
            register.launch(intent);
        });
    }

    private void setLongClickListenerInList() {
        listView.setOnItemLongClickListener((parent, view, index, id) -> {
            Contact selectedContact = Contact.contacts.get(index);
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);
            bundle.putInt("id", selectedContact.getId());
            bundle.putString("name", selectedContact.getName());
            bundle.putString("address", selectedContact.getAddress());
            bundle.putString("telephone", selectedContact.getTelephone());
            bundle.putString("cellphone", selectedContact.getCellphone());
            Intent intent = new Intent(this, ContactDetail.class);
            intent.putExtras(bundle);
            register.launch(intent);
            return false;
        });
    }

    private void setPhoneListAdapter() {
        phoneListAdapter = new PhoneListAdapter(getApplicationContext(), Contact.contacts);
        listView.setAdapter(phoneListAdapter);
        setLongClickListenerInList();
    }

    private void getContactListFromDatabase() {
        contactService.list();
    }

}