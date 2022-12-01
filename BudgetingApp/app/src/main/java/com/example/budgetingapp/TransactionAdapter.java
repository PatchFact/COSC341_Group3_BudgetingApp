package com.example.budgetingapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context mContext;
    private int mResource;

    public TransactionAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Transaction> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

    }
}
