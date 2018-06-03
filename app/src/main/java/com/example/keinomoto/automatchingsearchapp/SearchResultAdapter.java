package com.example.keinomoto.automatchingsearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    List<SearchInfo> searchInfoList;

    public SearchResultAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSearchInfoList(List<SearchInfo> searchInfoList) {
        this.searchInfoList = searchInfoList;
    }

    @Override
    public int getCount() {
        return searchInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return searchInfoList.get(position).getSearchId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.scrapingrow,parent,false);

        ((TextView)convertView.findViewById(R.id.title)).setText(searchInfoList.get(position).getTitle());

        return convertView;
    }
}
