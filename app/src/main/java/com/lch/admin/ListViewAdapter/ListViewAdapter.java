package com.lch.admin.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lch.admin.rest_client.R;

import java.util.ArrayList;
import java.util.Comparator;

public class ListViewAdapter extends ArrayAdapter<ContractRatioPrice>  {

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
        ViewHolder holder;
        // Inflate the rowlayout.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView= inflater.inflate(R.layout.rowlayout, parent, false);
            holder = new ViewHolder();
            holder.contract= (TextView) rowView.findViewById(R.id.contract);
            holder.radio = (TextView) rowView.findViewById(R.id.ratio);
            holder.price = (TextView) rowView.findViewById(R.id.price);
            rowView.setTag(holder);

        }
        else {

            holder = (ViewHolder) rowView.getTag();
        }
        if (contractRatioPrice != null) {
            try {
                holder.contract.setText(contractRatioPrice.contract);
                holder.radio.setText(String.format("%.2f", contractRatioPrice.ratio));
                if (contractRatioPrice.contract.equals("XAG_USD")) {
                    holder.price.setText(String.format("%.3f",contractRatioPrice.price));
                }
                else if (contractRatioPrice.contract.equals("USD_JPY")) {
                    holder.price.setText(String.format("%.2f",contractRatioPrice.price));
                }
                else if (contractRatioPrice.contract.equals("XAU_USD")) {
                    holder.price.setText(String.format("%.1f",contractRatioPrice.price));
                }
                else {
                    holder.price.setText(String.format("%.4f", contractRatioPrice.price));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rowView;
    }
    @Override
    public void notifyDataSetChanged() {
        this.setNotifyOnChange(false);
        this.sort(new Comparator<ContractRatioPrice>() {
                            @Override public int compare(ContractRatioPrice c1, ContractRatioPrice c2) {
                                return Float.compare(c2.ratio, c1.ratio);
                            }
                        });

        this.setNotifyOnChange(true);
    }
}
