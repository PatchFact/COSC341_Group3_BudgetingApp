package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

public class TransactionsOverview extends AppCompatActivity {

    Button addButton;
    ListView transactionListView;

    double[] amounts;
    String[] dates;
    String[] accounts;
    String[] notes;
    String[] colors;

    private Document XML;

    List<Transaction> allTransactions = new ArrayList<>();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_overview);

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

        for (int i = 0; i < allTransactions.size(); i++) {
            int size = allTransactions.size();

            amounts = new double[size];
            dates = new String[size];
            accounts = new String[size];
            notes = new String[size];
            colors = new String[size];
        }

        transactionListView = findViewById(R.id.transaction_list);


        Toast.makeText(this, "" + String.format("%.2f", allTransactions.get(0).amount), Toast.LENGTH_SHORT).show();
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

    public class CustomListView extends ListActivity {

    }
}