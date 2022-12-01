package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EnvelopeAdd extends AppCompatActivity {

    int envelopeColor = 0xFFFFFFFF;
    Button colorPickerButton;
    Button confirmButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envelope_add);

        colorPickerButton = (Button) findViewById(R.id.colorPickerButton);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEnvelopeToCSV();
                Intent intent = new Intent(EnvelopeAdd.this, EnvelopesOverview.class);
                startActivity(intent);
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, envelopeColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                envelopeColor = color;
                System.out.println(Integer.toUnsignedString(envelopeColor,16));
                System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "BudgetingApp" + "/" + "data.xml");
            }
        });
        colorPicker.show();
    }

    private void addEnvelopeToCSV() {
        EditText name = (EditText) findViewById(R.id.editNameText);
        EditText budget = (EditText) findViewById(R.id.editBudgetText);
        String fileContents =  name.getText() + "," + budget.getText() + "," + Integer.toUnsignedString(envelopeColor,16) + "\n";
        try {
            FileOutputStream outputStream = openFileOutput("envelopes.csv", Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}