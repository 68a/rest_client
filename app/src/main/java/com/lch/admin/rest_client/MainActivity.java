package com.lch.admin.rest_client;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.Response.ErrorListener;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ArrayList<HashMap<String, String>> feedList;
    SimpleAdapter simpleAdapter;
    ListView lv;
    String apiKey;
    String apiUrlBegin;
    String apiUrlEnd;

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
    protected void getRest() {
       // String url = "http://httpbin.org/html";
        String url = apiUrlBegin + "XAG_USD&" + apiUrlEnd;

// Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        System.out.println("rest_client: " + response.substring(0,100));

                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("rest_client: Something went wrong!");
                error.printStackTrace();

            }
        });

// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void runThread() {

        new Thread() {
            public void run() {

                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                feedList.clear();
                                simpleAdapter.notifyDataSetChanged();
                                lv.invalidateViews();
                                lv.refreshDrawableState();
                                btn.setText("#" + 100);
                                String apiKey = getStringValue("api_key");
                                System.out.println("rest_client: " + apiKey);
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getConfig();
        getRest();
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        btn = (Button)findViewById(R.id.btn);

        feedList= new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("date", "1/7");
        map.put("description", "gift her");
        map.put("price", "23");
        map.put("discount", "25");
        feedList.add(map);

        map = new HashMap<String, String>();
        map.put("date", "1/8");
        map.put("description", "nice phone");
        map.put("price", "67");
        map.put("discount", "50");
        feedList.add(map);

        map = new HashMap<String, String>();
        map.put("date", "1/6");
        map.put("description", "hello");
        map.put("price", "33");
        map.put("discount", "50");
        feedList.add(map);


        map = new HashMap<String, String>();
        map.put("date", "1/3");
        map.put("description", "yo");
        map.put("price", "123");
        map.put("discount", "33");
        feedList.add(map);



        map = new HashMap<String, String>();
        map.put("date", "1/2");
        map.put("description", "nice phone");
        map.put("price", "67");
        map.put("discount", "50");
        feedList.add(map);



        map = new HashMap<String, String>();
        map.put("date", "23/12");
        map.put("description", "nice car");
        map.put("price", "6700");
        map.put("discount", "50");
        feedList.add(map);


        map = new HashMap<String, String>();
        map.put("date", "4/3");
        map.put("description", "nice phone");
        map.put("price", "678");
        map.put("discount", "70");
        feedList.add(map);

        map = new HashMap<String, String>();
        map.put("date", "1/12");
        map.put("description", "Ymmy burger");
        map.put("price", "12");
        map.put("discount", "10");
        feedList.add(map);

        simpleAdapter = new SimpleAdapter(this, feedList, R.layout.view_item, new String[]{"date", "description", "price", "discount"},
                new int[]{R.id.textViewDate, R.id.textViewDescription, R.id.textViewDiscount, R.id.textViewPrice});
        lv.setAdapter(simpleAdapter);
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
