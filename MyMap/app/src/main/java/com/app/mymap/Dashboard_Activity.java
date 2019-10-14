package com.app.mymap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);

    }

    public void google_map(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void AR(View view) {
        Intent intent = new Intent(this,ARActivity.class);
        startActivity(intent);
    }
    public void leaderboard(View view) {
        Intent intent = new Intent(this,JavaMysqlSelect.class);
        startActivity(intent);
    }
}
