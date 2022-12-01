package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.Objects;

public class AddTransaction extends AppCompatActivity {

    Spinner divisionSpinner;
    Button submitButton;
    EditText firstName, lastName, studentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        getSupportActionBar().setTitle("Add Transaction");

        //Initialize
        firstName = findViewById(R.id.firstname_edit_text);
        lastName = findViewById(R.id.lastname_edit_text);
        studentNum = findViewById(R.id.student_num_edit_text);


        //Populate Spinner
        divisionSpinner = (Spinner) findViewById(R.id.division_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.division_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(adapter);

        //Populate Spinner
        divisionSpinner = (Spinner) findViewById(R.id.division_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.division_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divisionSpinner.setAdapter(adapter);

        //Button to submit data and write to file
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentNum.setError(null);

                //Check for valid user id
                if(studentNum.length() != 8) {
                    studentNum.setError("This field is required");
                    Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
                }

                //Build output
                String filename = "myTransactions.txt";
                String fileContents = String.join(
                        ",",
                        studentNum.getText().toString(),
                        lastName.getText().toString(),
                        firstName.getText().toString(),
                        selectedGender.getText().toString(),
                        divisionSpinner.getSelectedItem().toString()
                ) + "\n";

                //Write to file
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_APPEND);
                    outputStream.write(fileContents.getBytes());
                    outputStream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }
}