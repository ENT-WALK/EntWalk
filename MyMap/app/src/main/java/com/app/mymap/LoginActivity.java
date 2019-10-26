package com.app.mymap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.app.mymap.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference users;
    EditText edtUsername,edtPassword;
    Button btnSignIn;
    TextView edtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtRegister = (TextView)findViewById(R.id.edtRegister);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        edtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUsername.getText().toString(),
                        edtPassword.getText().toString());
            }
        });
    }

    private void signIn(final String username,final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                if(!username.isEmpty()){
                    User login = dataSnapshot.child(username).getValue(User.class);
                    if(login.getPassword().equals(password)){
                        Toast.makeText(LoginActivity.this, "Success Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), WelcomePage.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Password is Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password is Not Registered", Toast.LENGTH_SHORT).show();
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
