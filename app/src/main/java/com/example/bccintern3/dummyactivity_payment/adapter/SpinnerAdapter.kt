package com.example.bccintern3.dummyactivity_payment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.bccintern3.R
import com.squareup.picasso.Picasso

class SpinnerAdapter(private var nama:ArrayList<String>,
                     private var icon:ArrayList<Int>
                                  ):BaseAdapter(){

    private lateinit var context:Context

    override fun getCount(): Int {
        return nama.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.universal_spinner_layout,parent,false)

        var icon = view.findViewById<ImageView>(R.id.universal_spinner_image)
        val txt = view.findViewById<TextView>(R.id.universal_spinner_text)

        txt.setText(nama.get(position))

        if(position!=0){
            Picasso
                .get()
                .load(this.icon.get(position-1))
                .into(icon)
        }

        return view
    }
}