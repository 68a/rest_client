package com.lch.admin.rest_client;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lch.admin.ListViewAdapter.ContractRatioPrice;
import com.lch.admin.ListViewAdapter.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.android.volley.Response.ErrorListener;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ArrayList<HashMap<String, String>> feedList;

    ListView lv;
    String apiKey;
    String apiUrlBegin;
    String apiUrlEnd;
    String tickTime;
    TextView timeText;
    ListViewAdapter adapter;
    String [] contracts = {"XAU_USD", "XAG_USD", "EUR_USD", "NZD_USD", "AUD_USD", "GBP_USD","USD_JPY", "USD_CHF"};

    private String getStringValue(String key) {
        // Retrieve the resource id
        String packageName = getBaseContext().getPackageName();
        Resources resources = getBaseContext().getResources();
        int stringId = resources.getIdentifier(key, "string", packageName);
        if (stringId == 0) { return null; }
        // Return the string value based on the res id
        return resources.getString(stringId);
    }
    public void getConfig() {
        apiKey = getStringValue("apiKey");
        apiUrlBegin = getStringValue("apiUrlBegin");
        apiUrlEnd = getStringValue("apiUrlEnd");
    }
    protected void getRest(final String contractName) {
       // String url = "http://httpbin.org/html";

        String url = apiUrlBegin + contractName + "&" + apiUrlEnd;

// Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject json;
                        JSONObject contract;
                        JSONArray jArr;
                        JSONArray obj;

                        // Result handling
                        try {
                            json = new JSONObject(response);
                            contract = json.getJSONObject("data");
                            JSONObject xag = contract.getJSONObject(contractName);
                            jArr = xag.getJSONArray("data");
                            obj = jArr.getJSONArray(jArr.length() - 1);
                            System.out.println("rest_client resp: " + response);
                            System.out.println("rest_client json" + json.toString());
                            System.out.println("rest_client data:" + obj.toString());

                            ContractRatioPrice contractRatioPrice = new ContractRatioPrice("", "0","0");
                            ArrayList<ContractRatioPrice> newData = ContractRatioPrice.fromJson(obj, contractName);

                            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date tick = new Date(Long.parseLong( obj.get(0).toString())*1000L);
                            tickTime = sdf.format(tick);

                            timeText = (TextView) findViewById(R.id.tickTime);
                            timeText.setText(tickTime);


                            adapter.addAll(newData);
                            adapter.notifyDataSetChanged();
                            lv.invalidateViews();
                            lv.refreshDrawableState();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("rest_client: Something went wrong!");
                error.printStackTrace();

            }
        }){
            @Override
            public HashMap<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-type", "application/x-www-form-urlencoded");
                params.put("Accept", "text/plain");
                params.put("Authorization", "Bearer " + apiKey);
                return params;
            }
        };

// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void runThread() {

        new Thread() {
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        adapter.clear();
                        for (String s: contracts) {
                            getRest(s);
                        }

                    }
                });

            }

        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getConfig();

        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        // Inflate header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.header, lv,false);
        // Add header view to the ListView
        lv.addHeaderView(headerView);
        // Get the string array defined in strings.xml file

        // Create an adapter to bind data to the ListView
        ContractRatioPrice contractRatioPrice = new ContractRatioPrice("","0","0");
        ArrayList<ContractRatioPrice> contractRatioPrices = new ArrayList<>();
        contractRatioPrices.add(contractRatioPrice);
        adapter=new ListViewAdapter(this, contractRatioPrices );
        // Bind data to the ListView
        lv.setAdapter(adapter);
        runThread();

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runThread();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
