package com.example.budgetingapp;

public class Transaction {
    public final double amount;
    public final String date;
    public final String account;
    public final String note;
    public final String color;

    Transaction(double amount, String account, String date, String note, String color) {
        this.amount = amount;
        this.account = account;
        this.date = date;
        this.note = note;
        this.color = color;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getAccount() {
        return account;
    }

    public String getNote() {
        return note;
    }

    public String getColor() {
        return color;
    }
}
