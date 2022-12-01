package com.example.budgetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetingapp.databinding.ActivityEnvelopesOverviewBinding;

import java.util.ArrayList;
import java.util.List;

public class EnvelopesOverview extends DrawerBaseActivity implements AdapterView.OnItemClickListener {

    ActivityEnvelopesOverviewBinding activityEnvelopesOverviewBinding;

    ListView lvEnvelope;
    ArrayList<String> envelopes = new ArrayList<String>();
    ImageView editImg;
    ImageView deleteImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEnvelopesOverviewBinding = ActivityEnvelopesOverviewBinding.inflate(getLayoutInflater());
        setContentView(activityEnvelopesOverviewBinding.getRoot());

        allocateActivityTitle("Envelopes Overview");

        lvEnvelope = findViewById(R.id.lvEnvelope);
        generateListContent();
        ArrayAdapter<String> envelopeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, envelopes);
        lvEnvelope.setAdapter(new MyListAdapter(this, R.layout.list_item, envelopes));
    }

    //TODO: Replace random data method with method that gets envelope information for the current month in XML
    private void generateListContent() {
        for(int i = 0; i < 19; i++) {
            envelopes.add("This is a row: ");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String envelope = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Clicked: " + envelope, Toast.LENGTH_SHORT).show();
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.edit = (ImageView) convertView.findViewById(R.id.editImg);
                viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Edit button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.delete = (ImageView) convertView.findViewById(R.id.deleteImg);
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Delete button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.name = (TextView) convertView.findViewById(R.id.envelopeName);
                viewHolder.budget = (TextView) convertView.findViewById(R.id.envelopeBudget);

                convertView.setTag(viewHolder);

            }

            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.name.setText(getItem(position));
            mainViewHolder.budget.setText(Integer.toString(position));

            return convertView;
        }
    }

    public class ViewHolder {
        ImageView edit;
        ImageView delete;
        TextView name;
        TextView budget;

    }
}