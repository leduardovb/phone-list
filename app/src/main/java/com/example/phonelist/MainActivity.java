package com.example.phonelist;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
                int lastSavedContactIndex = Contact.contacts.size() - 1;
                Contact savedContact = Contact.contacts.get(lastSavedContactIndex);
                contactService.newContact(savedContact);
                phoneListAdapter.notifyDataSetChanged();
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
        listView.setOnClickListener(view -> {
            Contact selectedContact = Contact.contacts.get(0);
            Intent intent = new Intent(this, ContactDetail.class);
            intent.putExtra("contact", (Parcelable) selectedContact);
        });
    }

    private void setPhoneListAdapter() {
        phoneListAdapter = new PhoneListAdapter(getApplicationContext(), Contact.contacts);
        listView.setAdapter(phoneListAdapter);
    }

    private void getContactListFromDatabase() {
        contactService.list();
    }

}