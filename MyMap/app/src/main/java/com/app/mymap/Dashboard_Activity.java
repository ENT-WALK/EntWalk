package com.app.mymap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_);
        Button btnDialog = (Button)findViewById(R.id.dialog);
        btnDialog.setOnClickListener(new OnClickListener() {
            public void onClick (View v) {
                final Dialog dialog = new Dialog(Dashboard_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customdialog);
                dialog.setCancelable(true);

                Button button1 = (Button)dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
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
    public void minigame(View view) {
        Intent intent = new Intent(this, MiniGame.class);
        startActivity(intent);
    }
    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
         startActivity(intent);
    }

}
