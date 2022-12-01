package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.15f;
    private static final float MAX_X_VALUE = 6f;

    private BarChart chart;

    private BarData createChartData(int cur_month) {

        ArrayList<IBarDataSet> data_sets = new ArrayList<>();
        Scanner scan_envelopes, scan_transactions;

        //Create list of envelopes
        ArrayList<String> envelope_list = new ArrayList<>();
        try {
            File Envelopes = new File("/data/data/com.example.budgetingapp/files/Envelopes.csv");
            scan_envelopes = new Scanner(Envelopes);
        } catch(FileNotFoundException e) {
            return null;
        }

        while (scan_envelopes.hasNextLine()) {
            String line = scan_envelopes.nextLine();
            envelope_list.add(line.split(",")[1]);
        }

        for (int i = 0; i < envelope_list.size(); i++) {

            //Create 0 array of floats (one element for each month)
            float[] amounts = new float[12];
            for (int j = 0; j < amounts.length; j++)
                amounts[j] = 0F;

            //Loop over each transaction, check if it belongs to current envelope, if True add
            //transaction to array under correct month index
            try {
                File Transactions = new File("/data/data/com.example.budgetingapp/files/Transactions.csv");
                scan_transactions = new Scanner(Transactions);
            } catch(FileNotFoundException e) {
                return null;
            }

            while (scan_transactions.hasNext()) {
                String[] line = scan_transactions.nextLine().split(",");
                String trans_envelope = line[1];
                if (trans_envelope.equals(envelope_list.get(i))) {
                    int month = Integer.parseInt(line[3].substring(3, 5)) - 1;
                    float amt = Float.parseFloat(line[2]);
                    amounts[month] = amounts[month] + amt;
                }
            }

            //Create list of bar entries for past 6 months
            ArrayList<BarEntry> bar_entries = new ArrayList<>();
            int n = 0;
            //Should wrap around to last year if cur_month <6
            for (int j = cur_month - 5; j <= cur_month; j++) {
                bar_entries.add(new BarEntry(n, amounts[j]));
                n++;
            }

            //Add bar entries to graph data set with label name
            BarDataSet bar_data_set = new BarDataSet(bar_entries, envelope_list.get(i));
            bar_data_set.setColor(ColorTemplate.MATERIAL_COLORS[i]);
            data_sets.add(bar_data_set);
        }

        BarData graph_data = new BarData(data_sets);
        return graph_data;
    }
    private void prepareChartData(BarData data) {
        chart.setData(data);
        chart.getBarData().setBarWidth(BAR_WIDTH);
        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * 3);
        chart.groupBars(0, groupSpace, BAR_SPACE);
        chart.invalidate();
    }
    private void configureChartAppearance() {
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(true);

        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(MAX_X_VALUE);
    }
    private double getBudget() {
        double budget = 0.0;
        try {
            File F = new File("/data/data/com.example.budgetingapp/files/Envelopes.csv");
            Scanner scanner = new Scanner(F);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] values = data.split(",");
                budget = budget + Double.parseDouble(values[0]);
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            return 0.0;
        }
        return budget;
    }
    private double getSpent(int cur_month) {
        double spent = 0.0;
        try {
            File F = new File("/data/data/com.example.budgetingapp/files/Transactions.csv");
            Scanner scanner = new Scanner(F);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] line = data.split(",");
                int month = Integer.parseInt(line[3].substring(3,5))-1;
                if (cur_month == month)
                    spent = spent + Double.parseDouble(line[2]);
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            return 0.0;
        }
        return spent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Create chart
        chart = findViewById(R.id.barchart);

        BarData data = null;
        try {
            data = createChartData(11);
        } catch (Exception e) {
            e.printStackTrace();
        }
        configureChartAppearance();
        prepareChartData(data);


        //Set textView values
        Double amt_budget = getBudget();
        Double amt_spent = getSpent(11);
        Double amt_left = amt_budget - amt_spent;

        TextView txt_budget = findViewById(R.id.textView2);
        TextView txt_spent = findViewById(R.id.textView3);
        TextView txt_remaining = findViewById(R.id.textView4);

        txt_budget.setText("$" + String.format("%,.2f", amt_budget) + " Budget");
        txt_spent.setText("$" + String.format("%,.2f", amt_spent) + " Spent");
        txt_remaining.setText("$" + String.format("%,.2f", amt_left) + " Remaining");

    }
}