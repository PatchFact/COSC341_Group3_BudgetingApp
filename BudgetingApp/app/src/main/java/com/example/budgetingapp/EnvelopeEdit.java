package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.budgetingapp.databinding.ActivityEnvelopeEditBinding;

public class EnvelopeEdit extends DrawerBaseActivity {

    ActivityEnvelopeEditBinding activityEnvelopeEditBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEnvelopeEditBinding = ActivityEnvelopeEditBinding.inflate(getLayoutInflater());
        setContentView(activityEnvelopeEditBinding.getRoot());

        allocateActivityTitle("Envelope Edit");
    }
}