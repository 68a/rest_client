package com.lch.admin.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ContractRatioPrice {
    public String contract;
    public float ratio;
    public float price;

    public ContractRatioPrice(String contract, String ratio, String price) {
        this.contract = contract;
        this.ratio = Math.abs(50 - Float.parseFloat(ratio));
        this.price = Float.parseFloat(price);
    }

    public ContractRatioPrice(JSONArray jsonArr, String contractName) {
        try {
            this.contract = contractName;
            this.ratio = Math.abs(50 - Float.parseFloat(jsonArr.get(1).toString()));
            this.price = Float.parseFloat(jsonArr.get(2).toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<ContractRatioPrice> fromJson(JSONArray jsonObjects, String contractName) {
        ArrayList<ContractRatioPrice> contractRatioPrices = new ArrayList<>();

        contractRatioPrices.add(new ContractRatioPrice(jsonObjects, contractName));

        return contractRatioPrices;
    }
}

