package com.example.a1725121023_wengyuxian_lesson13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText editText;
    Button button1, button2, button3;
    TextView textView;
    ListView listView;
    Cursor cursor;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setDataAndType(uri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                    startActivity(intent);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, "WengYuxian");
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "001@bighard.com");
                intent.putExtra(
                        ContactsContract.Intents.Insert.EMAIL_TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "857857");
                intent.putExtra(
                        ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(this);
    }

    public void queryContacts() {
        String[] FROM_COLUMNS = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
        int[] TO_IDS = {android.R.id.text1};

        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        };

        String selection;
        String[] selectionArgs = {""};
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " DESC";

        String searchString = editText.getText().toString();
        if (TextUtils.isEmpty(searchString)) {
            selection = null;
            selectionArgs = null;
        } else {
            selection = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";
            selectionArgs[0] = "%" + searchString + "%";
        }

        cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                cursor,
                FROM_COLUMNS,
                TO_IDS,
                0
        );
        listView.setAdapter(cursorAdapter);
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            queryContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    queryContacts();
                } else {
                    Toast.makeText(MainActivity.this, "You denied the 'Read Contact permision'", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cursor.moveToPosition(position);
        long contactId = cursor.getLong(0);
        String contentKey = cursor.getString(1);
        uri = ContactsContract.Contacts.getLookupUri(contactId, contentKey);

        StringBuilder content = new StringBuilder();
        content.append("Name:").append(cursor.getString(2)).append("\n");

        String[] projection = {ContactsContract.CommonDataKinds.Email.ADDRESS};
        String selection = ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?";
        String[] selectionArgs = {""};
        selectionArgs[0] = Long.toString(contactId);

        Cursor emailCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );

        try {
            if (emailCursor.moveToFirst()) {
                int i = 1;
                do {
                    content.append("Email")
                            .append(i)
                            .append(":")
                            .append(emailCursor.getString(0))
                            .append("\n");
                    ++i;
                } while (emailCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            emailCursor.close();
        }

        String[] projection2 = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection2 = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String[] selectionArgs2 = {""};
        selectionArgs2[0] = Long.toString(contactId);
        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection2,
                selection2,
                selectionArgs2,
                null
        );
        try {
            if (phoneCursor.moveToFirst()) {
                int i = 1;
                do {
                    content.append("Phone" + i + ":" + phoneCursor.getString(0) + "\n");
                    i = i + 1;
                } while (phoneCursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            phoneCursor.close();
        }

        textView.setText(content);
    }
}
