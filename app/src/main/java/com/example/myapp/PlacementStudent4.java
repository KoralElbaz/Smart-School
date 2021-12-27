package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementStudent4 extends AppCompatActivity{

    private FirebaseDatabase database;
    private DatabaseReference Teachers;
    private DatabaseReference Lessons;
    private Teacher teacher;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_student4);

        database = FirebaseDatabase.getInstance();
        Teachers = database.getReference("Teachers");
        Lessons = database.getReference("Lessons");

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        teacher=new Teacher();

        ArrayList<String> emailList=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();



        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,emailList);

        Intent intent = this.getIntent();

        if(intent!=null)
        {
            String grade = intent.getStringExtra("grade");
            String uidStudent=intent.getStringExtra("uidStudent");
            String emailStudent=intent.getStringExtra("emailStudent");
            String Lesson_name=intent.getStringExtra("Lesson_name");



            Teachers.child(Lesson_name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds :snapshot.getChildren()){
                        teacher = ds.getValue(Teacher.class);
                        emailList.add(teacher.getEmail());
                        uidList.add(teacher.getFbUID());


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
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    HashMap hashMap=new HashMap();
                    hashMap.put("Grade","No score");
                    Lessons.child(Lesson_name).child("students_CourseGrade").child(uidStudent).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            HashMap hashMap2=new HashMap();
                            hashMap2.put("Absence","0");
                            Lessons.child(Lesson_name).child("students_CourseGrade").child(uidStudent).updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    HashMap hashMap3=new HashMap();
                                    hashMap3.put(uidList.get(position),Lesson_name);
                                    Lessons.child(Lesson_name).child("teacher_LessonName").updateChildren(hashMap3).addOnSuccessListener(new OnSuccessListener() {
                                        @Override
                                        public void onSuccess(Object o) {
                                            Toast.makeText(PlacementStudent4.this, "The Placement Successfully", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getApplicationContext(),menuSecretary.class);
                                            startActivity(i);
                                        }
                                    });
                                }
                            });

                        }
                    });
                }
            });
        }

    }

}