package com.example.phonelist;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private PhoneListAdapter phoneListAdapter;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        onResult();
        setFloatingButton();
        setPhoneListAdapter();

    }

    private void initWidgets() {
        listView = findViewById(R.id.contacts);
    }

    private void onResult() {
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == RESULT_OK) {

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

    private void setPhoneListAdapter() {
        phoneListAdapter = new PhoneListAdapter(getApplicationContext(), Contact.contacts);
        listView.setAdapter(phoneListAdapter);
    }

    private void getContactListFromDatabase() {
        SQLiteManager sqLiteManager = SQLiteManager.insanceOfDatabse(this);
        sqLiteManager.updateContactList();
    }


}