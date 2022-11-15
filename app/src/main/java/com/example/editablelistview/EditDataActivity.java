package com.example.editablelistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";
    private Button btnSave, btnDelete;
    private EditText editable_item;

    DatabaseHelper myDB;
    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSave = (Button) findViewById(R.id.btnSave);
        editable_item = (EditText) findViewById(R.id.editable_item);
        myDB = new DatabaseHelper(this);

        // Get the intent extra from 'ViewContentsList' class
        Intent receivedIntent = getIntent();

        // Now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1);

        // Now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        // Set the text to show the current selected name
        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    myDB.updateName(item, selectedID, selectedName);
                    toastMessage("Database updated successfully !!");
                }
                else
                    toastMessage("You must enter a name !!");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.deleteName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("Removed from database !!");
            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
