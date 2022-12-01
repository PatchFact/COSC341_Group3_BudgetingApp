package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.budgetingapp.databinding.ActivityAddTransactionBinding;

public class AddTransaction extends DrawerBaseActivity {

    ActivityAddTransactionBinding activityAddTransactionBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddTransactionBinding = ActivityAddTransactionBinding.inflate(getLayoutInflater());
        setContentView(activityAddTransactionBinding.getRoot());

        allocateActivityTitle("Add Transaction");
    }
}