package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetingapp.databinding.ActivityTransactionsOverviewBinding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TransactionsOverview extends DrawerBaseActivity {

    ActivityTransactionsOverviewBinding activityTransactionsOverviewBinding;

    Button addButton;
    TextView amount1;
    TextView amount2;
    TextView amount3;
    TextView amount4;

    private Document XML;

    public static class Transaction {
        public final double amount;
        public final String date;
        public final String account;
        public final String note;
        public final String color;

        private Transaction(double amount, String account, String date, String note, String color) {
            this.amount = amount;
            this.account = account;
            this.date = date;
            this.note = note;
            this.color = color;
        }
    }

    List<Transaction> allTransactions = new ArrayList<>();

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
            initializeXMLDoc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            allTransactions = readTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }

        amount1 = findViewById(R.id.amount1);
        amount2 = findViewById(R.id.amount2);
        amount3 = findViewById(R.id.amount3);
        amount4 = findViewById(R.id.amount4);


        Toast.makeText(this, "" + String.format("%.2f", allTransactions.get(0).amount), Toast.LENGTH_SHORT).show();

        amount1.setText(String.format("%.2f", allTransactions.get(0).amount) + allTransactions.get(0).color);
        amount2.setText(String.format("%.2f", allTransactions.get(1).amount) + allTransactions.get(1).color);
        amount3.setText(String.format("%.2f", allTransactions.get(2).amount) + allTransactions.get(2).color);
        amount4.setText(String.format("%.2f", allTransactions.get(3).amount) + allTransactions.get(3).color);

    }

    private void initializeXMLDoc() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        //TODO: add try/catch
        @SuppressLint("SdCardPath") File inputFile = new File("/data/data/com.example.budgetingapp/data.xml");
        XML = dBuilder.parse(inputFile);
        XML.getDocumentElement().normalize();
    }

    private List<Transaction> readTransactions() throws Exception {

        NodeList envelopes = XML.getElementsByTagName("Envelope");
        List<Transaction> transactions = new ArrayList<>();

        //Loop over each envelope
        for (int i = 0; i < envelopes.getLength(); i++) {

            Node N = envelopes.item(i);
            Element envelope = (Element) N;
            String env_color = envelope.getElementsByTagName("Color").item(0).getTextContent();

            //Loop over every transaction within envelope
            if (N.getNodeType() == Node.ELEMENT_NODE) {

                //Get all transactions inside envelope
                NodeList envelopeTransactions = envelope.getElementsByTagName("Transaction");

                for (int j = 0; j < envelopeTransactions.getLength(); j++) {

                    Element transaction = (Element) envelopeTransactions.item(j);

                    double amount = Double.parseDouble(transaction.getElementsByTagName("Amount").item(0).getTextContent());
                    String date = transaction.getElementsByTagName("Date").item(0).getTextContent();
                    String account = transaction.getElementsByTagName("Account").item(0).getTextContent();
                    String note = transaction.getElementsByTagName("Note").item(0).getTextContent();

                    transactions.add(new Transaction(amount, account, date, note, env_color));
                }
            }
        }

        return transactions;
    }

//    private List<Transaction> readTransactions() throws Exception {
//        //Get all transactions
//        NodeList transactionNodes = XML.getElementsByTagName("Transaction");
//        List<Transaction> transactions = new ArrayList<>();
//
//        //Loop for every transaction
//        for (int i = 0; i < transactionNodes.getLength(); i++) {
//            Node N = transactionNodes.item(i);
//            Element transaction = (Element) N;
//
//            double amount = Double.parseDouble(transaction.getElementsByTagName("Amount").item(0).getTextContent());
//            String date = transaction.getElementsByTagName("Date").item(0).getTextContent();
//            String account = transaction.getElementsByTagName("Account").item(0).getTextContent();
//            String note = transaction.getElementsByTagName("Note").item(0).getTextContent();
//
//            transactions.add(new Transaction(amount, account, date, note));
//        }
//
//        return transactions;
//    }
}