package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.example.budgetingapp.databinding.ActivityAddTransactionBinding;

public class AddTransaction extends DrawerBaseActivity {

    ActivityAddTransactionBinding activityAddTransactionBinding;

    boolean editing = false;
    int transactionPosition;

    boolean checksPassed = false;

    CheckBox incomeCheck;
    Spinner env_spinner, account_spinner;
    Button submitButton;
    Button deleteButton;
    //TODO: change the date edit to something good
    //TODO: limit amount add to 2 decimal places (currency input)
    EditText dateEdit, noteEdit, amountEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddTransactionBinding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(activityAddTransactionBinding.getRoot());

        allocateActivityTitle("Add Transaction");


        Intent intent = this.getIntent();

        //Check if we are editing
        if (intent.hasExtra("date")) {
            editing = true;

            Toast.makeText(this, "Editing!", Toast.LENGTH_SHORT).show();
            allocateActivityTitle("Edit transaction");
        }

        //Initialize
        dateEdit = findViewById(R.id.date_edit);
        noteEdit = findViewById(R.id.note_edit);
        amountEdit = findViewById(R.id.amount_edit);
        incomeCheck = findViewById(R.id.incomeCheck);

        //Populate Spinners
        env_spinner = findViewById(R.id.env_spinner);
        ArrayAdapter<CharSequence> env_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.env_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        env_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        env_spinner.setAdapter(env_adapter);

        account_spinner = findViewById(R.id.account_spinner);
        ArrayAdapter<CharSequence> acc_adapter = ArrayAdapter.createFromResource(
                this,
                R.array.account_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        acc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(acc_adapter);

        //Editing behaviour
        if (editing) {
            Bundle bundle = intent.getExtras();

            //Set defaults to be those of transaction being edited
            //Set income check
            if ( Double.parseDouble(bundle.getString("amount")) >= 0) {
                incomeCheck.setChecked(true);
            }

            //Set spinners
            ArrayAdapter spinnerAdapter = (ArrayAdapter) env_spinner.getAdapter();
            int spinnerPosition = spinnerAdapter.getPosition(bundle.getString("envelope"));
            env_spinner.setSelection(spinnerPosition);

            spinnerAdapter = (ArrayAdapter) account_spinner.getAdapter();
            spinnerPosition = spinnerAdapter.getPosition(bundle.getString("account"));
            account_spinner.setSelection(spinnerPosition);

            //Set TextEdits
            dateEdit.setText(bundle.getString("date"));
            amountEdit.setText(bundle.getString("amount").replace("-",""));
            noteEdit.setText(bundle.getString("note"));

            //Set transaction position for data submission
            transactionPosition = bundle.getInt("position");
        }

        //Button to delete data in editing mode
        if (editing) {
            deleteButton = findViewById(R.id.delete_button);
            //Make delete button visible
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> {
                deleteRecord(transactionPosition);
                Intent i = new Intent(AddTransaction.this, TransactionsOverview.class);
                startActivity(i);
            });
        }

        //Button to submit data and write to file
        submitButton = findViewById(R.id.add_transaction_button);
        submitButton.setOnClickListener(v -> {

            //Validate
            checksPassed = checkFields();
            if (checksPassed) {

                String color = "";

                //TODO: make this interact with the envelopes values?
                if (env_spinner.getSelectedItem().toString().equals("Groceries")) {
                    color = "#72cde1";
                } else if (env_spinner.getSelectedItem().toString().equals("Transportation")) {
                    color = "#e89d52";
                }

                //Build output
                @SuppressLint("DefaultLocale") String amount = String.format("%.2f", Double.parseDouble(amountEdit.getText().toString()));

                if(!incomeCheck.isChecked()) {
                    amount = "-" + amount;
                }

                String filename = "myTransactions.csv";
                String fileContents = String.join(
                        ",",
                        amount,
                        dateEdit.getText().toString(),
                        account_spinner.getSelectedItem().toString(),
                        noteEdit.getText().toString(),
                        color,
                        env_spinner.getSelectedItem().toString()
                ) + "\n";

                if (editing) {
                    deleteRecord(transactionPosition);
                }

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

                Intent i = new Intent(AddTransaction.this, TransactionsOverview.class);
                startActivity(i);
            }
        });
    }

    private void deleteRecord(int transactionPosition) {
        transactionPosition += 1;
        FileOutputStream outputStream;

        File dir = getFilesDir();
        File oldFile = new File(dir,"myTransactions.csv");

        int line = 0;
        String currentLine;

        String fileContents = "";

        try {
            FileInputStream fis = openFileInput("myTransactions.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while((currentLine = br.readLine()) != null) {
                line++;

                if(transactionPosition != line) {
                    fileContents += (currentLine + "\n");
                }
            }

            oldFile.delete();

            outputStream = openFileOutput("myTransactions.csv", Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

            fis.close();
            isr.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkFields() {
        dateEdit.setError(null);
        amountEdit.setError(null);

        int falseCount = 0;

        boolean[] checks = new boolean[] {
                dateEdit.length() > 0,
                amountEdit.length() > 0,
        };

        for (boolean check: checks) {
            if (!check) {
                falseCount++;
            }
        }

        for (int i = 0; i < checks.length; i++) {

            if(falseCount > 1) {
                Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show();

                dateEdit.setError("This field is required");
                amountEdit.setError("This field is required");

                return false;
            }

            if (!checks[i]) {

                switch (i) {
                    case 0:
                        dateEdit.setError("This field is required");
                        Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        amountEdit.setError("This field is required");
                        Toast.makeText(this, "Amount is required", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        }

        return true;

    }
}