package com.example.trackingcorona;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class Dashboard extends AppCompatActivity {
    private TextView todayaffected, todaydeath, todayrecovered, todaytotalaffected, todaytotaldeath, todaytotalrecovered,updatedash;
    private PieChart mPieChart;
    private Button stateoption;
    private LottieAnimationView loading;
    private ConstraintLayout cl1;
    private static  final int SPLASH_SCREEN=4000;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    rotected void onStart() {
        super.onStart();

        if(!isConnected(Dashboard.this))
        {
            loading.setVisibility(View.GONE);
            cl1.setVisibility(View.GONE);
            showCustomDialog();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(Dashboard dash) {

        ConnectivityManager cm = (ConnectivityManager) dash.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
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
                        startActivity(new Intent(Dashboard.this,Dashboard.class));
                        finish();

                    }
                })
                .show();


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        loading = findViewById(R.id.loading);
        cl1 = findViewById(R.id.foreground);



        todayaffected = (TextView) findViewById(R.id.todayaffected);
        todaydeath = findViewById(R.id.todaydeath);
        todayrecovered = findViewById(R.id.todaytotalrecovered);
        todaytotalaffected= findViewById(R.id.totalactive);
        todaytotaldeath= findViewById(R.id.todaytotaldeath);
        todaytotalrecovered= findViewById(R.id.todayrecovered);
        mPieChart = (PieChart) findViewById(R.id.piechart);
        stateoption = findViewById(R.id.stateoption);
        updatedash = findViewById(R.id.updatedash);

        LocalDate todayDate = LocalDate.now();

        updatedash.setText("Last Updated On "+String.valueOf(todayDate));

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Dashboard.this);
        JsonObjectRequest jsonobjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://disease.sh/v3/covid-19/historical/India?lastdays=2", null, new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = response.getJSONObject("timeline");
                    JSONObject affected = obj.getJSONObject("cases");

                    LocalDate todayDate = LocalDate.now();
                    String dateonedaypprev = String.valueOf(findPrevOneDay(todayDate));
                    String s1[] = dateonedaypprev.split("-");
                    char year[]= s1[0].toCharArray();
                    String years=String.valueOf(year[2])+String.valueOf(year[3]);
                    int n = affected.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2]+"/"+years);

                    String datetwodayprev = String.valueOf(findPrevTwoDay(todayDate));
                    String s2[] = datetwodayprev.split("-");
                    char year1[]= s2[0].toCharArray();
                    String years1=String.valueOf(year1[2])+String.valueOf(year1[3]);
                    int n1 = affected.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2]+"/"+years1);

                    int n2= n - n1;
                    String s = String.valueOf(n2);
                    todayaffected.setText(s);
                    todaytotalaffected.setText(String.valueOf(n));

                    JSONObject deaths = obj.getJSONObject("deaths");
                    int d1= deaths.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2]+"/"+years);
                    int d2= deaths.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2]+"/"+years1);
                    int d3 = d1-d2;
                    s=String.valueOf(d3);
                    todaydeath.setText(s);
                    todaytotaldeath.setText(String.valueOf(d1));

                    JSONObject recovered = obj.getJSONObject("recovered");
                    int r1= recovered.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2]+"/"+years);
                    int r2= recovered.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2]+"/"+years1);
                    int r3 = r1-r2;
                    s=String.valueOf(r3);
                    todayrecovered.setText(s);
                    todaytotalrecovered.setText(String.valueOf(r1));

                    updatedash.setText("Last Updated On "+String.valueOf(todayDate));

                    loading.setVisibility(View.GONE);
                    cl1.setVisibility(View.GONE);
                    

                    mPieChart.addPieSlice(new PieModel("Total Death", d1, Color.parseColor("#641E16")));
                    mPieChart.addPieSlice(new PieModel("Total Affected", n1, Color.parseColor("#9A7D0A")));
                    mPieChart.addPieSlice(new PieModel("Total Recovered", r1, Color.parseColor("#2E86C1")));

                    mPieChart.startAnimation();





                }
                catch(JSONException e)
                {
                    //e.printStackTrace();


                    try {
                        JSONObject obj = response.getJSONObject("timeline");
                        JSONObject affected = obj.getJSONObject("cases");

                        LocalDate todayDate = LocalDate.now();
                        String dateonedaypprev = String.valueOf(findPrevTwoDay(todayDate));
                        String s1[] = dateonedaypprev.split("-");
                        char year[]= s1[0].toCharArray();
                        String years=String.valueOf(year[2])+String.valueOf(year[3]);
                        int n = affected.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2]+"/"+years);

                        String datetwodayprev = String.valueOf(findPrevThreeDay(todayDate));
                        String s2[] = datetwodayprev.split("-");
                        char year1[]= s2[0].toCharArray();
                        String years1=String.valueOf(year1[2])+String.valueOf(year1[3]);
                        int n1 = affected.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2]+"/"+years1);

                        int n2= n - n1;
                        String s = String.valueOf(n2);
                        todayaffected.setText(s);
                        todaytotalaffected.setText(String.valueOf(n));

                        JSONObject deaths = obj.getJSONObject("deaths");
                        int d1= deaths.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2]+"/"+years);
                        int d2= deaths.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2]+"/"+years1);
                        int d3 = d1-d2;
                        s=String.valueOf(d3);
                        todaydeath.setText(s);
                        todaytotaldeath.setText(String.valueOf(d1));

                        JSONObject recovered = obj.getJSONObject("recovered");
                        int r1= recovered.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2]+"/"+years);
                        int r2= recovered.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2]+"/"+years1);
                        int r3 = r1-r2;
                        s=String.valueOf(r3);
                        todayrecovered.setText(s);
                        todaytotalrecovered.setText(String.valueOf(r1));

                        updatedash.setText("Last Updated On "+String.valueOf(findPrevOneDay(todayDate)));

                        loading.setVisibility(View.GONE);
                        cl1.setVisibility(View.GONE);


                        mPieChart.addPieSlice(new PieModel("Total Death", d1, Color.parseColor("#641E16")));
                        mPieChart.addPieSlice(new PieModel("Total Affected", n1, Color.parseColor("#9A7D0A")));
                        mPieChart.addPieSlice(new PieModel("Total Recovered", r1, Color.parseColor("#2E86C1")));

                        mPieChart.startAnimation();

                    }
                    catch(Exception f)
                    {
                        e.printStackTrace();
                    }



                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("Shubham", "Something went wrong" + error);
                Toast.makeText(Dashboard.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonobjectRequest);


        stateoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.setVisibility(View.VISIBLE);
                cl1.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        startActivity(new Intent(Dashboard.this,StateOptions.class));
                        loading.setVisibility(View.GONE);
                        cl1.setVisibility(View.GONE);
                        //finish();
                    }
                }, SPLASH_SCREEN);
            }
        });



    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate findPrevOneDay(LocalDate localdate)
    {
        return localdate.minusDays(1);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate findPrevTwoDay(LocalDate localdate)
    {
        return localdate.minusDays(2);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate findPrevThreeDay(LocalDate localdate)
    {
        return localdate.minusDays(3);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#6BD5D4'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        System.exit(1);
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#6BD5D4'>No</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}