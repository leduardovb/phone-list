package com.example.phonelist;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phonelist.adapters.PhoneListAdapter;
import com.example.phonelist.models.AlertModal;
import com.example.phonelist.models.Contact;
import com.example.phonelist.models.Notification;
import com.example.phonelist.services.ContactService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final int SWAP_LIMIT = 70;
    private static final int SWAP_VELOCITY_LIMIT = 70;

    private ListView listView;
    private PhoneListAdapter phoneListAdapter;
    private ActivityResultLauncher<Intent> register;
    private AlertModal alertModal;
    private final ContactService contactService = new ContactService(this);
    private final Notification notification = new Notification(this);
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        initWidgets();
        onResult();
        setFloatingButton();
        getContactListFromDatabase();
        setPhoneListAdapter();
        showNotificationLink();
        buildAlertModal();
        gestureDetector = new GestureDetector(this, new GestureListener(this, register, alertModal, phoneListAdapter));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void launchActivity() {
        Intent intent = new Intent(this, ContactDetail.class);
        register.launch(intent);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    };

    private void buildAlertModal() {
        alertModal = new AlertModal(MainActivity.this);
        alertModal.buildModal("Tem certeza de sua escolha?", "Podemos fazer do jeito difícil ou fácil... Qual vai escolher?", true);
    }

    private void initWidgets() {
        listView = findViewById(R.id.contacts);
    }

    private void onResult() {
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) phoneListAdapter.notifyDataSetChanged();
        });
    }

    private void setFloatingButton() {
        FloatingActionButton addContactButton = findViewById(R.id.add);
        addContactButton.setOnClickListener(view -> {
            launchActivity();
        });
    }

    static public void showAlertModal(Context context, AlertModal alertModal, ActivityResultLauncher<Intent> register, PhoneListAdapter phoneListAdapter, Integer index) {
        DialogInterface.OnClickListener onClickPositiveButton = (dialog, which) -> {
            Contact contact = Contact.contacts.get(index);
            ContactService service = new ContactService(context);
            if (service.deleteContact(contact)) {
                Contact.contacts.remove(contact);
                phoneListAdapter.notifyDataSetChanged();
            }
        };

        DialogInterface.OnClickListener onClickNegativeButton = (dialog, which) -> {
            Bundle bundle = generateContactBundleByIndex(index);
            Intent intent = new Intent(context, ContactDetail.class);
            intent.putExtras(bundle);
            register.launch(intent);
        };

        alertModal.setPositiveButton("Excluir", onClickPositiveButton);
        alertModal.setNegativeButton("Alterar", onClickNegativeButton);
        alertModal.show();
    }

    static private Bundle generateContactBundleByIndex(Integer index) {
        Contact selectedContact = Contact.contacts.get(index);

        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putInt("id", selectedContact.getId());
        bundle.putString("name", selectedContact.getName());
        bundle.putString("address", selectedContact.getAddress());
        bundle.putString("telephone", selectedContact.getTelephone());
        bundle.putString("cellphone", selectedContact.getCellphone());

        return bundle;
    }

    private void setLongClickListenerInList() {
        listView.setOnItemLongClickListener((parent, view, index, id) -> {
            showAlertModal(MainActivity.this, alertModal, register, phoneListAdapter, index);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setPhoneListAdapter() {
        phoneListAdapter = new PhoneListAdapter(getApplicationContext(), Contact.contacts);
        listView.setOnTouchListener(touchListener);
        listView.setAdapter(phoneListAdapter);
        setLongClickListenerInList();
    }

    private void getContactListFromDatabase() {
        contactService.list();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.CHANNEL_ID), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager;
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotificationLink() {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.grupointegrado.br/"));
        notification.showNotification("Conheça o novo site do Integrado", "Clique aqui para conhecer o novo site do Integrado.", R.drawable.message_icon, notificationIntent);
    }

    static class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private final ActivityResultLauncher<Intent> register;
        private final Context context;
        private final AlertModal alertModal;
        private final PhoneListAdapter phoneListAdapter;

        public GestureListener(Context context, ActivityResultLauncher<Intent> register, AlertModal alertModal, PhoneListAdapter phoneListAdapter) {
            this.context = context;
            this.register = register;
            this.alertModal = alertModal;
            this.phoneListAdapter = phoneListAdapter;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            float xDifference = event1.getX() - event2.getX();
            if (validateSwapping(xDifference, velocityX)) {
                if (isRightSwapping(xDifference)) {
                    Intent intent = new Intent(context, ContactDetail.class);
                    register.launch(intent);
                } else {
                    int listSize = Contact.contacts.size();
                    if (listSize >= 1) MainActivity.showAlertModal(context, alertModal, register, phoneListAdapter, listSize - 1);
                }
            }
            return true;
        }

        private boolean isRightSwapping(float xDifference) {
            return xDifference > 0;
        }

        private boolean validateSwapping(float xDifference, float velocityX) {
            return Math.abs(xDifference) > SWAP_LIMIT && Math.abs(velocityX) > SWAP_VELOCITY_LIMIT;
        }
    }
}