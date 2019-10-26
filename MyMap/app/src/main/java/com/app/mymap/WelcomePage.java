package com.app.mymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_);

    }
    public void Letgo(View view) {
        Intent intent = new Intent(this, Dashboard_Activity.class);
        startActivity(intent);
    }

}
