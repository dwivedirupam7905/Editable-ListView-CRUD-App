package com.example.editablelistview;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewContentsList extends AppCompatActivity {

    private static final String TAG = "ViewContentsList";
    DatabaseHelper myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        ListView listView = findViewById(R.id.listView);
        myDB = new DatabaseHelper(this);

        Log.d(TAG, "populateListView: Displaying data in the ListView...");

        // Get the data and append to a list
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();

        if(data.getCount() == 0)
            Toast.makeText(ViewContentsList.this, "Database is empty!!", Toast.LENGTH_LONG).show();
        else{
            while(data.moveToNext()){
                // Get the value from database in column1
                // Then add it to the arraylist
                theList.add(data.getString(1));
                // Create the list adapter and set the adapter
                ListAdapter listAdapter =
                        new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }

            // Set an onItemClickListener to the listView
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = adapterView.getItemAtPosition(i).toString();
                    Log.d(TAG,"onItemClick: You clicked on " + name);

                    // Get the ID associated with that name
                    Cursor data = myDB.getItemID(name);
                    int itemID = -1;
                    while(data.moveToNext())
                        itemID = data.getInt(0);
                    if(itemID > -1) {
                        Log.d(TAG, "onItemClick: The ID is: " + itemID);
                        Intent editScreenIntent = new Intent(ViewContentsList.this, EditDataActivity.class);
                        editScreenIntent.putExtra("id", itemID);
                        editScreenIntent.putExtra("name", name);
                        startActivity(editScreenIntent);
                    }

                    else
                        Toast.makeText(ViewContentsList.this, "No ID is associated with this name !!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
