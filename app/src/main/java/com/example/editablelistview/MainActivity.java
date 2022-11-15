package com.example.editablelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    DatabaseHelper myDB;
    Button btnAdd, btnView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        myDB = new DatabaseHelper(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewContentsList.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length() != 0){
                    AddData(newEntry);
                    editText.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "You must put something in the text field !!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void AddData(String newEntry){
        boolean insertData = myDB.addData(newEntry);

        if(insertData)
            Toast.makeText(MainActivity.this, "Data inserted successfully !!",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Something went wrong !!",
                    Toast.LENGTH_LONG).show();
    }
}