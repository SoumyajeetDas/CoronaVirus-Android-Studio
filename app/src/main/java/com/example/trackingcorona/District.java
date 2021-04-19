package com.example.trackingcorona;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class District extends AppCompatActivity {

    private TextView confirm,death,recovered,todaytotalconfirmed,todaytotaldeath,todaytotalrecovered,districtname;
    private PieChart mPieChart;
    private LottieAnimationView loading;
    private ConstraintLayout cl1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(District d) {

        ConnectivityManager cm = (ConnectivityManager) d.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(District.this);
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
                        startActivity(new Intent(District.this,StateOptions.class));
                        finish();

                    }
                })
                .show();




    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        getSupportActionBar().hide();

        loading = findViewById(R.id.loading);
        cl1 = findViewById(R.id.foreground);

        if(!isConnected(District.this))
        {
            loading.setVisibility(View.GONE);
            cl1.setVisibility(View.GONE);
            showCustomDialog();
        }

            Intent intent = getIntent();
            String confirmed = intent.getStringExtra("confirm");
            String name = intent.getStringExtra("name");
            String recovery = intent.getStringExtra("recovery");
            String deaths = intent.getStringExtra("death");
            String todayconfirmed = intent.getStringExtra("todayconfirm");
            String todaydeath = intent.getStringExtra("todaydeath");
            String todayrecovery = intent.getStringExtra("todayrecovery");

            districtname = findViewById(R.id.districtname);
            confirm = findViewById(R.id.totalconfirm);
            recovered = findViewById(R.id.todayrecovered);
            death = findViewById(R.id.todaytotaldeath);
            todaytotalconfirmed = findViewById(R.id.todayconfirmed);
            todaytotaldeath = findViewById(R.id.todaydeath);
            todaytotalrecovered = findViewById(R.id.todaytotalrecovered);
            mPieChart = (PieChart) findViewById(R.id.piechart1);

            districtname.setText(name);
            confirm.setText(confirmed);
            recovered.setText(recovery);
            death.setText(deaths);
            todaytotalconfirmed.setText(todayconfirmed);
            todaytotaldeath.setText(todaydeath);
            todaytotalrecovered.setText(todayrecovery);

            loading.setVisibility(View.GONE);
            cl1.setVisibility(View.GONE);

            mPieChart.addPieSlice(new PieModel("Total Death", Integer.parseInt(deaths), Color.parseColor("#641E16")));
            mPieChart.addPieSlice(new PieModel("Total Confirmed", Integer.parseInt(confirmed), Color.parseColor("#9A7D0A")));
            mPieChart.addPieSlice(new PieModel("Total Recovered", Integer.parseInt(recovery), Color.parseColor("#2E86C1")));


            mPieChart.startAnimation();





    }
}