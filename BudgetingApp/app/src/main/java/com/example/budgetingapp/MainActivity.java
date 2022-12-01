package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;

import com.example.budgetingapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

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

public class MainActivity extends DrawerBaseActivity {


    ActivityMainBinding activityMainBinding;

    Button transaction_button;
    Button envelope_button;
    Button dashboard_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        allocateActivityTitle("Dashboard");




                startActivity(intent);
            }
        });

        envelope_button = findViewById(R.id.envelope_button);
        envelope_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnvelopesOverview.class);

                startActivity(intent);
            }
        });
        
        dashboard_button = findViewById(R.id.dashboard_button);
        dashboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);

                startActivity(intent);
            }
        });

    }
}
