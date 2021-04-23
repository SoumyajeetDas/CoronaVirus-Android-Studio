package com.example.trackingcorona;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictOptions extends AppCompatActivity {
    private RecyclerView rv;
    private LottieAnimationView loading;
    private ConstraintLayout cl1;

    ArrayList<DistrictData> al = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(DistrictOptions dop) {

        ConnectivityManager cm = (ConnectivityManager) dop.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCon = cm.getNetworkInfo(cm.TYPE_WIFI);
        NetworkInfo mobileCon = cm.getNetworkInfo(cm.TYPE_MOBILE);

        if((wifiCon != null && wifiCon.isConnected() )|| (mobileCon!= null && mobileCon.isConnected()))
        {
            return true;
        }
        else {
            return false;
        }

    }

    private void showCustomDialog() {
        // Creatin g Popup
        AlertDialog.Builder builder = new AlertDialog.Builder(DistrictOptions.this);
        builder.setMessage("Please connect to Internet")
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#6BD5D4'>Connect</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#6BD5D4'>Cancel</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(DistrictOptions.this,StateOptions.class));
                        finish();

                    }
                })
                .show();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_options);
        getSupportActionBar().hide();

        rv = findViewById(R.id.recyclerView);
        loading = findViewById(R.id.loading);
        cl1 = findViewById(R.id.foreground);

        if(!isConnected(DistrictOptions.this))
        {
            loading.setVisibility(View.GONE);
            cl1.setVisibility(View.GONE);
            showCustomDialog();
        }

            Intent intent = getIntent();
            String statename = intent.getStringExtra("statename");


            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(DistrictOptions.this);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://api.covid19india.org/v2/state_district_wise.json", null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        int flag=0;
                        al.clear();
                        for (int i=1;i<response.length();i++){
                            JSONObject jsonObjectState = response.getJSONObject(i);

                            if (statename.toLowerCase().equals(jsonObjectState.getString("state").toLowerCase())){
                                JSONArray jsonArrayDistrict = jsonObjectState.getJSONArray("districtData");

                                for (int j=0; j<jsonArrayDistrict.length(); j++){
                                    JSONObject jsonObjectDistrict = jsonArrayDistrict.getJSONObject(j);
                                    String dis_name = jsonObjectDistrict.getString("district");
                                    String dis_cnf = jsonObjectDistrict.getString("confirmed");
                                    String dis_death = jsonObjectDistrict.getString("deceased");
                                    String dis_recovered = jsonObjectDistrict.getString("recovered");

                                    JSONObject jsonObjectDistNew = jsonObjectDistrict.getJSONObject("delta");
                                    String dis_newcnf = jsonObjectDistNew.getString("confirmed");
                                    String dis_newdeath = jsonObjectDistNew.getString("deceased");
                                    String dis_newrecovered = jsonObjectDistNew.getString("recovered");
                                    al.add(new DistrictData(dis_name,dis_cnf,dis_death,dis_recovered,dis_newcnf,dis_newdeath,dis_newrecovered));


                                }
                                rv.setLayoutManager(new LinearLayoutManager(DistrictOptions.this));
                                DistrictAdapter ad = new DistrictAdapter(al);
                                rv.setAdapter(ad);
                                loading.setVisibility(View.GONE);
                                cl1.setVisibility(View.GONE);
                                flag=1;
                            }
                            if (flag==1)
                                break;
                        }

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });
            requestQueue.add(jsonArrayRequest);




    }
}
