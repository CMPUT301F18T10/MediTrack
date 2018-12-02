package com.example.meditrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CareProviderRecordListAdapter extends BaseAdapter {
    private Context myContext;
    private ArrayList<CareProviderRecord> careProviderRecords;


    public CareProviderRecordListAdapter(ArrayList<CareProviderRecord> careProviderRecord, Context myContext)
    {

        this.myContext = myContext;
        this.careProviderRecords = careProviderRecord;
    }


    @Override
    public int getCount() {
        return careProviderRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.careprovider_record_list_item, parent, false);
        }
        TextView test4 = (TextView)convertView.findViewById(R.id.careproviderCommnet);
        test4.setText(((CareProviderRecord)careProviderRecords.get(position)).getCareProviderComment());

        return convertView;
    }
}
