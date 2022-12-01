package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.budgetingapp.databinding.ActivityTransactionsOverviewBinding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TransactionsOverview extends DrawerBaseActivity {

    ActivityTransactionsOverviewBinding activityTransactionsOverviewBinding;

public class TransactionsOverview extends AppCompatActivity {


    Button addButton;
    ListView transactionListView;

    ArrayList<Transaction> allTransactions = new ArrayList<>();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransactionsOverviewBinding = ActivityTransactionsOverviewBinding.inflate(getLayoutInflater());
        setContentView(activityTransactionsOverviewBinding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Transactions");

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(TransactionsOverview.this, AddTransaction.class);

            startActivity(intent);
        });

        try {
            allTransactions = readTransactions();
            Collections.sort(allTransactions);
            //TODO: Sort?
        } catch (Exception e) {
            e.printStackTrace();
        }

        transactionListView = findViewById(R.id.transaction_list);

        TransactionAdapter transactionAdapter = new TransactionAdapter(this, R.layout.row_layout_transaction, allTransactions);

        transactionListView.setAdapter(transactionAdapter);
    }

    private ArrayList<Transaction> readTransactions() {

        ArrayList<Transaction> transactions = new ArrayList<>();
        String line;

        try {
            FileInputStream fis = openFileInput("myTransactions.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",[ ]*", 6);

                Transaction transaction = new Transaction(
                        values[0],
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        values[5]
                );

                transactions.add(transaction);
            }

        } catch (IOException e){
            //In case the file does not exist, let the user know and return to main screen
            Toast.makeText(this, "Write your first transaction!", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }


        return transactions;
    }
}