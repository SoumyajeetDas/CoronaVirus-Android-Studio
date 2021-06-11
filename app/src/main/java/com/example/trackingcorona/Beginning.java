package com.example.trackingcorona;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class Beginning extends AppCompatActivity {

    private Button dash;
    private LottieAnimationView loading;
    private ConstraintLayout cl1;
    private static  final int SPLASH_SCREEN=4000;


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onStart() {
        super.onStart();

        if(!isConnected(Beginning.this))
        {
            loading.setVisibility(View.GONE);
            cl1.setVisibility(View.GONE);
            showCustomDialog();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(Beginning dash) {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Beginning.this);
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
                        startActivity(new Intent(Beginning.this,Beginning.class));
                        finish();

                    }
                })
                .show();


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning);
        getSupportActionBar().hide();
        dash = findViewById(R.id.option);

        loading = findViewById(R.id.loading);
        cl1 = findViewById(R.id.foreground);


        ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.cubiclinechart);
        ValueLineChart mCubicValueLineChart1 = (ValueLineChart) findViewById(R.id.cubiclinechart1);
        ValueLineChart mCubicValueLineChart2 = (ValueLineChart) findViewById(R.id.cubiclinechart2);




        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Beginning.this);
        JsonObjectRequest jsonobjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://disease.sh/v3/covid-19/historical/India?", null, new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = response.getJSONObject("timeline");
                    JSONObject affected = obj.getJSONObject("cases");


                    LocalDate todayDate = LocalDate.now();

                    String today=String.valueOf(todayDate);
                    String s[] = today.split("-");


                    // 25
                    String dateonedaypprev = String.valueOf(findPrevDay(todayDate,1));
                    String s1[] = dateonedaypprev.split("-");
                    char year[]= s1[0].toCharArray();
                    String years=String.valueOf(year[2])+String.valueOf(year[3]);
                    float n = (float)affected.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2].replaceFirst ("^0*", "")+"/"+years);
                    //getInt() is used to get the Integer Value with respect to key in the JSON object

                    //24
                    String datetwodayprev = String.valueOf(findPrevDay(todayDate,2));
                    String s2[] = datetwodayprev.split("-");
                    char year1[]= s2[0].toCharArray();
                    String years1=String.valueOf(year1[2])+String.valueOf(year1[3]);
                    float n1 = (float)affected.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2].replaceFirst ("^0*", "")+"/"+years1);

                    float a = n-n1;

                    //23
                    String datethreedayprev = String.valueOf(findPrevDay(todayDate,3));
                    String s3[] = datethreedayprev.split("-");
                    char year2[]= s3[0].toCharArray();
                    String years2=String.valueOf(year2[2])+String.valueOf(year2[3]);
                    float n2 = (float)affected.getInt(s3[1].replaceFirst ("^0*", "")+"/"+s3[2].replaceFirst ("^0*", "")+"/"+years2);

                    float b = n1-n2;

                    //22
                    String datefourdayprev = String.valueOf(findPrevDay(todayDate,4));
                    String s4[] = datefourdayprev.split("-");
                    char year3[]= s4[0].toCharArray();
                    String years3=String.valueOf(year3[2])+String.valueOf(year3[3]);
                    float n3 = (float)affected.getInt(s4[1].replaceFirst ("^0*", "")+"/"+s4[2].replaceFirst ("^0*", "")+"/"+years3);

                    float c = n2-n3;

                    //21
                    String datefivedayprev = String.valueOf(findPrevDay(todayDate,5));
                    String s5[] = datefivedayprev.split("-");
                    char year4[]= s5[0].toCharArray();
                    String years4=String.valueOf(year4[2])+String.valueOf(year4[3]);
                    float n4 = (float)affected.getInt(s5[1].replaceFirst ("^0*", "")+"/"+s5[2].replaceFirst ("^0*", "")+"/"+years4);

                    float d = n3-n4;

                    //20
                    String datesixdayprev = String.valueOf(findPrevDay(todayDate,6));
                    String s6[] = datesixdayprev.split("-");
                    char year5[]= s6[0].toCharArray();
                    String years5=String.valueOf(year5[2])+String.valueOf(year5[3]);
                    float n5 = (float)affected.getInt(s6[1].replaceFirst ("^0*", "")+"/"+s6[2].replaceFirst ("^0*", "")+"/"+years5);

                    float e = n4-n5;

                    //19
                    String datesevendayprev = String.valueOf(findPrevDay(todayDate,7));
                    String s7[] = datesevendayprev.split("-");
                    char year6[]= s7[0].toCharArray();
                    String years6=String.valueOf(year6[2])+String.valueOf(year6[3]);
                    float n6 = (float)affected.getInt(s7[1].replaceFirst ("^0*", "")+"/"+s7[2].replaceFirst ("^0*", "")+"/"+years6);

                    float f = n5-n6;


                    //18
                    String dateeightdayprev = String.valueOf(findPrevDay(todayDate,8));
                    String s8[] = dateeightdayprev.split("-");
                    char year7[]= s8[0].toCharArray();
                    String years7=String.valueOf(year7[2])+String.valueOf(year7[3]);
                    float n7 = (float)affected.getInt(s8[1].replaceFirst ("^0*", "")+"/"+s8[2].replaceFirst ("^0*", "")+"/"+years7);

                    float g = n6-n7;


                    JSONObject deaths = obj.getJSONObject("deaths");


                    //25
                    float d1= (float)deaths.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2].replaceFirst ("^0*", "")+"/"+years);
                    float d2= (float)deaths.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2].replaceFirst ("^0*", "")+"/"+years1);
                    float a1 = d1-d2;

                    //24
                    float d3= (float)deaths.getInt(s3[1].replaceFirst ("^0*", "")+"/"+s3[2].replaceFirst ("^0*", "")+"/"+years2);
                    float b1 = d2-d3;


                    //23
                    float d4= (float)deaths.getInt(s4[1].replaceFirst ("^0*", "")+"/"+s4[2].replaceFirst ("^0*", "")+"/"+years3);
                    float c1 = d3-d4;

                    //22
                    float d5= (float)deaths.getInt(s5[1].replaceFirst ("^0*", "")+"/"+s5[2].replaceFirst ("^0*", "")+"/"+years4);
                    float d11 = d4-d5;

                    //21
                    float d6= (float)deaths.getInt(s6[1].replaceFirst ("^0*", "")+"/"+s6[2].replaceFirst ("^0*", "")+"/"+years5);
                    float e1 = d5-d6;

                    //20
                    float d7= (float)deaths.getInt(s7[1].replaceFirst ("^0*", "")+"/"+s7[2].replaceFirst ("^0*", "")+"/"+years6);
                    float f1 = d6-d7;


                    //19
                    float d8= (float)deaths.getInt(s8[1].replaceFirst ("^0*", "")+"/"+s8[2].replaceFirst ("^0*", "")+"/"+years7);
                    float g1 = d7-d8;




                    JSONObject recovered = obj.getJSONObject("recovered");
                    float r1= (float)recovered.getInt(s1[1].replaceFirst ("^0*", "")+"/"+s1[2].replaceFirst ("^0*", "")+"/"+years);
                    float r2= (float)recovered.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2].replaceFirst ("^0*", "")+"/"+years1);
                    float p1 = r1-r2;

                    float r3= (float)recovered.getInt(s3[1].replaceFirst ("^0*", "")+"/"+s3[2].replaceFirst ("^0*", "")+"/"+years2);
                    float p2 = r2-r3;

                    float r4= (float)recovered.getInt(s4[1].replaceFirst ("^0*", "")+"/"+s4[2].replaceFirst ("^0*", "")+"/"+years3);
                    float p3 = r3-r4;


                    float r5= (float)recovered.getInt(s5[1].replaceFirst ("^0*", "")+"/"+s5[2].replaceFirst ("^0*", "")+"/"+years4);
                    float p4 = r4-r5;


                    float r6= (float)recovered.getInt(s6[1].replaceFirst ("^0*", "")+"/"+s6[2].replaceFirst ("^0*", "")+"/"+years5);
                    float p5 = r5-r6;


                    float r7= (float)recovered.getInt(s7[1].replaceFirst ("^0*", "")+"/"+s7[2].replaceFirst ("^0*", "")+"/"+years6);
                    float p6 = r6-r7;

                    float r8= (float)recovered.getInt(s8[1].replaceFirst ("^0*", "")+"/"+s8[2].replaceFirst ("^0*", "")+"/"+years7);
                    float p7 = r7-r8;




                    ValueLineSeries series = new ValueLineSeries();
                    series.setColor(0xFF9A7D0A);



                    series.addPoint(new ValueLinePoint(s6[2]+"/"+s6[1],g));
                    series.addPoint(new ValueLinePoint(s5[2]+"/"+s5[1],f));
                    series.addPoint(new ValueLinePoint(s4[2]+"/"+s4[1],e));
                    series.addPoint(new ValueLinePoint(s3[2]+"/"+s3[1],d));
                    series.addPoint(new ValueLinePoint(s2[2]+"/"+s2[1],c));
                    series.addPoint(new ValueLinePoint(s1[2]+"/"+s1[1],b));
                    series.addPoint(new ValueLinePoint(s[2]+"/"+s[1],a));




                    mCubicValueLineChart.addSeries(series);
                    mCubicValueLineChart.startAnimation();



                    ValueLineSeries series1 = new ValueLineSeries();
                    series1.setColor(0xFFC0392B);



                    series1.addPoint(new ValueLinePoint(s6[2]+"/"+s6[1],g1));
                    series1.addPoint(new ValueLinePoint(s5[2]+"/"+s5[1],f1));
                    series1.addPoint(new ValueLinePoint(s4[2]+"/"+s4[1],e1));
                    series1.addPoint(new ValueLinePoint(s3[2]+"/"+s3[1],d11));
                    series1.addPoint(new ValueLinePoint(s2[2]+"/"+s2[1],c1));
                    series1.addPoint(new ValueLinePoint(s1[2]+"/"+s1[1],b1));
                    series1.addPoint(new ValueLinePoint(s[2]+"/"+s[1],a1));




                    mCubicValueLineChart1.addSeries(series1);
                    mCubicValueLineChart1.startAnimation();




                    ValueLineSeries series2 = new ValueLineSeries();
                    series2.setColor(0xFF2E86C1);



                    series2.addPoint(new ValueLinePoint(s6[2]+"/"+s6[1],p7));
                    series2.addPoint(new ValueLinePoint(s5[2]+"/"+s5[1],p6));
                    series2.addPoint(new ValueLinePoint(s4[2]+"/"+s4[1],p5));
                    series2.addPoint(new ValueLinePoint(s3[2]+"/"+s3[1],p4));
                    series2.addPoint(new ValueLinePoint(s2[2]+"/"+s2[1],p3));
                    series2.addPoint(new ValueLinePoint(s1[2]+"/"+s1[1],p2));
                    series2.addPoint(new ValueLinePoint(s[2]+"/"+s[1],p1));




                    mCubicValueLineChart2.addSeries(series2);
                    mCubicValueLineChart2.startAnimation();

                    loading.setVisibility(View.GONE);
                    cl1.setVisibility(View.GONE);





                }
                catch(JSONException json)
                {


                    try {

                        JSONObject obj = response.getJSONObject("timeline");
                        JSONObject affected = obj.getJSONObject("cases");


                        LocalDate todayDate = LocalDate.now();

                        String today=String.valueOf(findPrevDay(todayDate,1));
                        String s[] = today.split("-");





                        //24
                        String datetwodayprev = String.valueOf(findPrevDay(todayDate,2));
                        String s2[] = datetwodayprev.split("-");
                        char year1[]= s2[0].toCharArray();
                        String years1=String.valueOf(year1[2])+String.valueOf(year1[3]);
                        float n1 = (float)affected.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2].replaceFirst ("^0*", "")+"/"+years1);


                        //23
                        String datethreedayprev = String.valueOf(findPrevDay(todayDate,3));
                        String s3[] = datethreedayprev.split("-");
                        char year2[]= s3[0].toCharArray();
                        String years2=String.valueOf(year2[2])+String.valueOf(year2[3]);
                        float n2 = (float)affected.getInt(s3[1].replaceFirst ("^0*", "")+"/"+s3[2].replaceFirst ("^0*", "")+"/"+years2);

                        float b = n1-n2;

                        //22
                        String datefourdayprev = String.valueOf(findPrevDay(todayDate,4));
                        String s4[] = datefourdayprev.split("-");
                        char year3[]= s4[0].toCharArray();
                        String years3=String.valueOf(year3[2])+String.valueOf(year3[3]);
                        float n3 = (float)affected.getInt(s4[1].replaceFirst ("^0*", "")+"/"+s4[2].replaceFirst ("^0*", "")+"/"+years3);

                        float c = n2-n3;

                        //21
                        String datefivedayprev = String.valueOf(findPrevDay(todayDate,5));
                        String s5[] = datefivedayprev.split("-");
                        char year4[]= s5[0].toCharArray();
                        String years4=String.valueOf(year4[2])+String.valueOf(year4[3]);
                        float n4 = (float)affected.getInt(s5[1].replaceFirst ("^0*", "")+"/"+s5[2].replaceFirst ("^0*", "")+"/"+years4);

                        float d = n3-n4;

                        //20
                        String datesixdayprev = String.valueOf(findPrevDay(todayDate,6));
                        String s6[] = datesixdayprev.split("-");
                        char year5[]= s6[0].toCharArray();
                        String years5=String.valueOf(year5[2])+String.valueOf(year5[3]);
                        float n5 = (float)affected.getInt(s6[1].replaceFirst ("^0*", "")+"/"+s6[2].replaceFirst ("^0*", "")+"/"+years5);

                        float e = n4-n5;

                        //19
                        String datesevendayprev = String.valueOf(findPrevDay(todayDate,7));
                        String s7[] = datesevendayprev.split("-");
                        char year6[]= s7[0].toCharArray();
                        String years6=String.valueOf(year6[2])+String.valueOf(year6[3]);
                        float n6 = (float)affected.getInt(s7[1].replaceFirst ("^0*", "")+"/"+s7[2].replaceFirst ("^0*", "")+"/"+years6);

                        float f = n5-n6;


                        //18
                        String dateeightdayprev = String.valueOf(findPrevDay(todayDate,8));
                        String s8[] = dateeightdayprev.split("-");
                        char year7[]= s8[0].toCharArray();
                        String years7=String.valueOf(year7[2])+String.valueOf(year7[3]);
                        float n7 = (float)affected.getInt(s8[1].replaceFirst ("^0*", "")+"/"+s8[2].replaceFirst ("^0*", "")+"/"+years7);

                        float g = n6-n7;


                        JSONObject deaths = obj.getJSONObject("deaths");


                        //25
                        float d2= (float)deaths.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2].replaceFirst ("^0*", "")+"/"+years1);

                        //24
                        float d3= (float)deaths.getInt(s3[1].replaceFirst ("^0*", "")+"/"+s3[2].replaceFirst ("^0*", "")+"/"+years2);
                        float b1 = d2-d3;


                        //23
                        float d4= (float)deaths.getInt(s4[1].replaceFirst ("^0*", "")+"/"+s4[2].replaceFirst ("^0*", "")+"/"+years3);
                        float c1 = d3-d4;

                        //22
                        float d5= (float)deaths.getInt(s5[1].replaceFirst ("^0*", "")+"/"+s5[2].replaceFirst ("^0*", "")+"/"+years4);
                        float d11 = d4-d5;

                        //21
                        float d6= (float)deaths.getInt(s6[1].replaceFirst ("^0*", "")+"/"+s6[2].replaceFirst ("^0*", "")+"/"+years5);
                        float e1 = d5-d6;

                        //20
                        float d7= (float)deaths.getInt(s7[1].replaceFirst ("^0*", "")+"/"+s7[2].replaceFirst ("^0*", "")+"/"+years6);
                        float f1 = d6-d7;


                        //19
                        float d8= (float)deaths.getInt(s8[1].replaceFirst ("^0*", "")+"/"+s8[2].replaceFirst ("^0*", "")+"/"+years7);
                        float g1 = d7-d8;




                        JSONObject recovered = obj.getJSONObject("recovered");

                        float r2= (float)recovered.getInt(s2[1].replaceFirst ("^0*", "")+"/"+s2[2].replaceFirst ("^0*", "")+"/"+years1);

                        float r3= (float)recovered.getInt(s3[1].replaceFirst ("^0*", "")+"/"+s3[2].replaceFirst ("^0*", "")+"/"+years2);
                        float p2 = r2-r3;

                        float r4= (float)recovered.getInt(s4[1].replaceFirst ("^0*", "")+"/"+s4[2].replaceFirst ("^0*", "")+"/"+years3);
                        float p3 = r3-r4;


                        float r5= (float)recovered.getInt(s5[1].replaceFirst ("^0*", "")+"/"+s5[2].replaceFirst ("^0*", "")+"/"+years4);
                        float p4 = r4-r5;


                        float r6= (float)recovered.getInt(s6[1].replaceFirst ("^0*", "")+"/"+s6[2].replaceFirst ("^0*", "")+"/"+years5);
                        float p5 = r5-r6;


                        float r7= (float)recovered.getInt(s7[1].replaceFirst ("^0*", "")+"/"+s7[2].replaceFirst ("^0*", "")+"/"+years6);
                        float p6 = r6-r7;

                        float r8= (float)recovered.getInt(s8[1].replaceFirst ("^0*", "")+"/"+s8[2].replaceFirst ("^0*", "")+"/"+years7);
                        float p7 = r7-r8;




                        ValueLineSeries series = new ValueLineSeries();
                        series.setColor(0xFF9A7D0A);



                        series.addPoint(new ValueLinePoint(s6[2]+"/"+s6[1],g));
                        series.addPoint(new ValueLinePoint(s5[2]+"/"+s5[1],f));
                        series.addPoint(new ValueLinePoint(s4[2]+"/"+s4[1],e));
                        series.addPoint(new ValueLinePoint(s3[2]+"/"+s3[1],d));
                        series.addPoint(new ValueLinePoint(s2[2]+"/"+s2[1],c));
                        series.addPoint(new ValueLinePoint(s[2]+"/"+s[1],b));




                        mCubicValueLineChart.addSeries(series);
                        mCubicValueLineChart.startAnimation();



                        ValueLineSeries series1 = new ValueLineSeries();
                        series1.setColor(0xFFC0392B);



                        series1.addPoint(new ValueLinePoint(s6[2]+"/"+s6[1],g1));
                        series1.addPoint(new ValueLinePoint(s5[2]+"/"+s5[1],f1));
                        series1.addPoint(new ValueLinePoint(s4[2]+"/"+s4[1],e1));
                        series1.addPoint(new ValueLinePoint(s3[2]+"/"+s3[1],d11));
                        series1.addPoint(new ValueLinePoint(s2[2]+"/"+s2[1],c1));
                        series1.addPoint(new ValueLinePoint(s[2]+"/"+s[1],b1));





                        mCubicValueLineChart1.addSeries(series1);
                        mCubicValueLineChart1.startAnimation();




                        ValueLineSeries series2 = new ValueLineSeries();
                        series2.setColor(0xFF2E86C1);



                        series2.addPoint(new ValueLinePoint(s6[2]+"/"+s6[1],p7));
                        series2.addPoint(new ValueLinePoint(s5[2]+"/"+s5[1],p6));
                        series2.addPoint(new ValueLinePoint(s4[2]+"/"+s4[1],p5));
                        series2.addPoint(new ValueLinePoint(s3[2]+"/"+s3[1],p4));
                        series2.addPoint(new ValueLinePoint(s2[2]+"/"+s2[1],p3));
                        series2.addPoint(new ValueLinePoint(s[2]+"/"+s[1],p2));





                        mCubicValueLineChart2.addSeries(series2);
                        mCubicValueLineChart2.startAnimation();

                        loading.setVisibility(View.GONE);
                        cl1.setVisibility(View.GONE);





                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("Shubham", "Something went wrong" + error);
                Toast.makeText(Beginning.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonobjectRequest);


        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.setVisibility(View.VISIBLE);
                cl1.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        startActivity(new Intent(Beginning.this,Dashboard.class));
                        loading.setVisibility(View.GONE);
                        cl1.setVisibility(View.GONE);
                        //finish();
                    }
                }, SPLASH_SCREEN);
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate findPrevDay(LocalDate localdate, int no)
    {
        return localdate.minusDays(no);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Beginning.this);
        builder.setTitle("Cororna Tracking");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#6BD5D4'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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