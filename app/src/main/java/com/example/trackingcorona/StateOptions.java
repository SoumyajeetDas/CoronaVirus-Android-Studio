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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StateOptions extends AppCompatActivity {

    private RecyclerView rv;
    private LottieAnimationView loading;
    private ConstraintLayout cl1;
    ArrayList<StateData> al = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(StateOptions so) {

        ConnectivityManager cm = (ConnectivityManager) so.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(StateOptions.this);
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
                        startActivity(new Intent(StateOptions.this,StateOptions.class));
                        finish();

                    }
                })
                .show();




    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_options);
        getSupportActionBar().hide();

        rv = findViewById(R.id.recyclerView);
        loading = findViewById(R.id.loading);
        cl1 = findViewById(R.id.foreground);

        if(!isConnected(StateOptions.this))
        {
            showCustomDialog();
            loading.setVisibility(View.GONE);
            cl1.setVisibility(View.GONE);
        }
        else {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(StateOptions.this);
            JsonObjectRequest jsonobjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://api.covid19india.org/data.json", null, new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        al.clear();
                        JSONArray jsonArray = response.getJSONArray("statewise");
                        for (int i = 1; i < jsonArray.length() ; i++)
                        {
                            Log.d("Soumyajeet","Entering jsonArray");
                            JSONObject statewise = jsonArray.getJSONObject(i);
                            String str_cnf = statewise.getString("confirmed"); //getString() is used to fetch the string data wrt to a key in Json
                            String str_recovery = statewise.getString("recovered");
                            String str_name = statewise.getString("state");
                            String str_death = statewise.getString("deaths");
                            String str_todaydeath = statewise.getString("deltadeaths");
                            String str_todayconfirmed = statewise.getString("deltaconfirmed");
                            String str_todayrecovered = statewise.getString("deltarecovered");
                            String str_lastupdatedtime = statewise.getString("lastupdatedtime");
                            al.add(new StateData(str_cnf, str_recovery,str_name,str_death, str_todaydeath,str_todayconfirmed, str_todayrecovered,str_lastupdatedtime));

                            //StateData sd = new StateData(str_active, str_cnf, str_recovery, str_name,str_updatetime);
                            //tv.setText(sd.getStr_recovery());

                        }

                        rv.setLayoutManager(new LinearLayoutManager(StateOptions.this));
                        StateAdapter ad = new StateAdapter(al);
                        rv.setAdapter(ad);
                        loading.setVisibility(View.GONE);
                        cl1.setVisibility(View.GONE);



                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.d("Shubham", "Something went wrong" + error);
                    Toast.makeText(StateOptions.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
            requestQueue.add(jsonobjectRequest);


        }







    }

}
