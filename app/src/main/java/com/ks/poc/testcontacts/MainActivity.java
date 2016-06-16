package com.ks.poc.testcontacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //----- KS ----------

        if (checkPermission()) {
         loadListView();
        };
    }


    private void loadListView() {
        Cursor cur = getContacts();
        ListView lv = (ListView) findViewById(R.id.listSearchResult);

//        String[] fields = new String[] {ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String[] fields = new String[] {ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this,
                        R.layout.contacts_list_item,
                        cur,
                        fields,
                        new int[] {R.id.txtName, R.id.txtNumber},0);
        lv.setAdapter(adapter);


        //--------------------
//        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//                "Android", "iPhone", "WindowsMobile" };
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.contacts_list_item,values);
//        lv.setAdapter(adapter);
        //---------------------------

//        String[] fields = new String[] {ContactsContract.Data.DISPLAY_NAME};
//
//        SimpleCursorAdapter adapter =
//                new SimpleCursorAdapter(this,
//                        R.layout.activity_main,
//                        cur,
//                        fields,
//                        new int[] {R.layout.contacts_list_item});
//        lv.setAdapter(adapter);
    }

    private Cursor getContacts() {
        // Run query
//        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
//        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("MainActivity", " Does not have READ_CONTACTS permission");

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    999);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 999: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", " READ_CONTACTS permission granted");
                    loadListView();
                } else {
                    Log.d("MainActivity", " User not granting READ_CONTACTS permission");
                }
                return;
            }
        }
    }
}
