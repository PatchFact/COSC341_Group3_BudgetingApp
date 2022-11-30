package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TransactionsOverview extends AppCompatActivity {

    Button addButton;

    private Document XML;

    public static class Transaction {
        public final double amount;
        public final String date;
        public final String account;
        public final String note;

        private Transaction(double amount, String account, String date, String note) {
            this.amount = amount;
            this.account = account;
            this.date = date;
            this.note = note;
        }
    }

    List<Transaction> allTransactions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_overview);

        getSupportActionBar().setTitle("Transactions");

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionsOverview.this, AddTransaction.class);

                startActivity(intent);
            }
        });

        try {
            initializeXMLDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            allTransactions = readTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initializeXMLDoc() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        //TODO: add try/catch
        File inputFile = new File("/data/data/com.example.chartjava/files/data.xml");
        XML = dBuilder.parse(inputFile);
        XML.getDocumentElement().normalize();
    }

    private List readTransactions() throws Exception {
        //Get all transactions
        NodeList transactionNodes = XML.getElementsByTagName("Transaction");
        List<Transaction> transactions = new ArrayList<>();

        //Loop for every transaction
        for (int i = 0; i < transactionNodes.getLength(); i++) {
            Node N = transactionNodes.item(i);
            Element transaction = (Element) N;

            double amount = Double.parseDouble(transaction.getElementsByTagName("Amount").item(0).getTextContent());
            String date = transaction.getElementsByTagName("Date").item(0).getTextContent();
            String account = transaction.getElementsByTagName("Account").item(0).getTextContent();
            String note = transaction.getElementsByTagName("Note").item(0).getTextContent();

            transactions.add(new Transaction(amount, date, account, note));
        }

        return transactions;
    }
}