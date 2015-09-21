/**
 * Created by Admin on 2015/9/18.
 */
package com.lch.admin.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lch.admin.rest_client.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<ContractRatioPrice>  {

    ArrayList<ContractRatioPrice> item_list;

    Context context;
    public ListViewAdapter(Context context, ArrayList<ContractRatioPrice> contractRatioPrices){
        super(context, R.layout.rowlayout, contractRatioPrices);
    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView contract;
        public TextView radio;
        public TextView price;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ContractRatioPrice contractRatioPrice = getItem(position);
        View rowView = convertView;
        ViewHolder holder = null;
        // Inflate the rowlayout.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView= inflater.inflate(R.layout.rowlayout, parent, false);
            holder = new ViewHolder();
            holder.contract= (TextView) rowView.findViewById(R.id.contract);
            holder.radio = (TextView) rowView.findViewById(R.id.radio);
            holder.price = (TextView) rowView.findViewById(R.id.price);
            rowView.setTag(holder);

        }
        else {
            // Set text to each TextView of ListView item
         //   String[] items = item_list[position].split("__");
            holder = (ViewHolder) rowView.getTag();
        }
        if (contractRatioPrice != null) {
            try {
                holder.contract.setText(contractRatioPrice.contract);
                holder.radio.setText(contractRatioPrice.ratio);
                holder.price.setText(contractRatioPrice.price);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowView;
    }

}
