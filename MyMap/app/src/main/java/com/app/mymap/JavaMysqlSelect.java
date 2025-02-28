package com.app.mymap;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class JavaMysqlSelect extends AppCompatActivity
{

    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_mysql_select);
        databaseReference=FirebaseDatabase.getInstance().getReference("Leaderboard");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,arrayList2);
        listView.setAdapter(arrayAdapter);
            mDatabase.child("Leaderboard").orderByChild("score").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        String str = child.getValue().toString();

                        String str2= str.replaceAll("\\{", "");
                        String str3= str2.replaceAll("\\, username="+child.getKey()+"\\}", "");
                        arrayList.add("  "+ child.getKey()+ "  " +str3);

                    }
                    Collections.reverse(arrayList);
                    for(int i=0;i<8;i++){
                        if(arrayList.get(i).isEmpty()){break;}
                        arrayList2.add(arrayList.get(i));
                    }
                    arrayAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
   }

}