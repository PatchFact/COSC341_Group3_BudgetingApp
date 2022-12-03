package com.example.budgetingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetingapp.databinding.ActivityTransactionsOverviewBinding;

import java.io.FileOutputStream;
import java.io.IOException;

public class reports_activity extends DrawerBaseActivity {

    //{0:envelope,1:account}
    public int num =-1;
    //{0:month view,1:month over month view}
    public int num2 =-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_activity);

        Button currentMonthEnvelope = findViewById(R.id.button);
        Button currentMonthAccount = findViewById(R.id.button2);
        Button backButton = findViewById(R.id.button5);

        String line = "1, 1, -50.70, 29-11-22, RBC, Save On Foods\n"+"1, 2, -32.45, 30-11-22, Scotiabank, Safeway\n+" +
                "1, 3, 100.00, 30-12-22, Scotiabank, Payday\n"+"2, 1, -80.50, 19-11-22, RBC, Gas\n"+"2, 2, -52.45, 29-11-22, Scotiabank, Gas\n"+
                "2, 3, -2, 30-11-22, Scotiabank, Bus\n"+"2, 3, -220, 14-12-22, RBC, Insurance\n"+"2, 1, -24.45, 10-12-22, RBC, Car Wash\n";
        try{
            FileOutputStream writer = openFileOutput("transactions.csv", Context.MODE_APPEND);
            writer.write(line.getBytes());
            writer.close();
            //Toast.makeText(getApplicationContext(), "Wrote to "+getApplicationContext().getFilesDir(), Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            Toast.makeText(getApplicationContext(), "Failed to write. An error occurred", Toast.LENGTH_SHORT).show();
        }


        currentMonthEnvelope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(reports_activity.this, reports_summary_activity_envelope.class);
                num =0;
                num2=0;
                intent.putExtra("monthView",num2);
                intent.putExtra("summaryType",num);
                startActivity(intent);
            }
        });

        currentMonthAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(reports_activity.this, reports_summary_activity_envelope.class);
                num=1;
                num2=0;
                intent.putExtra("monthView",num2);
                intent.putExtra("summaryType",num);
                startActivity(intent);
            }
        });


        //using finish method, so it will go back to the activity that was open previous [ie another main task]
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
}