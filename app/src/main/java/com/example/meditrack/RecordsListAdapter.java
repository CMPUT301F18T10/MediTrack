package com.example.meditrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class RecordsListAdapter extends BaseAdapter
{
    private Context myContext;
    private ArrayList<PatientRecord> patientRecords;


    public RecordsListAdapter(ArrayList<PatientRecord> patientRecords, Context myContext)
    {
        this.patientRecords = patientRecords;
        this.myContext = myContext;
    }


    @Override
    public int getCount()
    {
        return patientRecords.size();
    }
    @Override
    public Object getItem(int position)
    {
        return null;
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.record_list_row_item, parent, false);
        }
        TextView text1 = (TextView)convertView.findViewById(R.id.recordTitle);
        TextView text2 = (TextView)convertView.findViewById(R.id.recordDescription);
        TextView text3 = (TextView)convertView.findViewById(R.id.recordtimeStamp);
        text1.setText(((PatientRecord)patientRecords.get(position)).getTitle());
        text2.setText(((PatientRecord)patientRecords.get(position)).getDescription());
        text3.setText(((PatientRecord)patientRecords.get(position)).getTimestamp().toString());

        return convertView;
    }
}
