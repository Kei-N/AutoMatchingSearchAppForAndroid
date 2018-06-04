package com.example.keinomoto.automatchingsearchapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import java.util.ArrayList

class SearchResultAdapter(internal var context: Context) : BaseAdapter() {
    internal var layoutInflater: LayoutInflater? = null
    internal var searchInfoList = mutableListOf<SearchInfo>()

    init {
        this.layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    fun setSearchInfoList(searchInfoList: MutableList<SearchInfo>) {
        this.searchInfoList = searchInfoList
    }

    override fun getCount(): Int {
        return searchInfoList.size
    }

    override fun getItem(position: Int): Any {
        return searchInfoList[position]
    }

    override fun getItemId(position: Int): Long {
        return searchInfoList[position].searchId!!.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        convertView = layoutInflater!!.inflate(R.layout.scrapingrow, parent, false)

        (convertView.findViewById<View>(R.id.title) as TextView).text = searchInfoList[position].title

        return convertView
    }
}
