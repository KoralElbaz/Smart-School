package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class gradesTeacher extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;
    private Student student;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_teacher);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        ClassRoom = database.getReference("ClassRoom");
        student=new Student();

        ArrayList<String> emailList=new ArrayList<>();
        ArrayList<String> fullNameList=new ArrayList<>();
        ArrayList<String> idList=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();


        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,emailList);

        ClassRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds :snapshot.getChildren()){
                    student = ds.getValue(Student.class);
                    emailList.add(student.getEmail());
                    fullNameList.add(student.getFull_name());
                    idList.add(student.getId()+"");
                    uidList.add(student.getFbUID());


                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // don't ignore errors
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(),updateGrades.class);
                i.putExtra("email",emailList.get(position));
                i.putExtra("id",idList.get(position));
                i.putExtra("full_name",fullNameList.get(position));
                i.putExtra("uid",uidList.get(position));


                startActivity(i);
            }
        });


    }
}