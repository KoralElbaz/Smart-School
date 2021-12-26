package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class updateGrade_levelTwo extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference Teachers;
    private DatabaseReference Lessons;
    private FirebaseAuth sAuth;
    private String lessonName;
    private Student student;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_student2);

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.listview);
        Teachers = database.getReference("Teachers");
        Lessons = database.getReference("Lessons");
        sAuth = FirebaseAuth.getInstance();
        student=new Student();

        Teachers.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.hasChild(Objects.requireNonNull(sAuth.getUid()))){
                        Teacher t = new Teacher();
                        lessonName=Objects.requireNonNull(ds.child(sAuth.getUid()).getValue(Teacher.class)).getName_lesson();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayList<String> emailList=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();
        Map<String,Student> studentMap=new HashMap<>();



        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,emailList);

        Intent intent = this.getIntent();

        if(intent!=null)
        {
            String grade = intent.getStringExtra("grade");

            Lessons.child(lessonName).child(grade).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds :snapshot.getChildren()){
                        student = ds.getValue(Student.class);
                        emailList.add(student.getEmail());
                        uidList.add(student.getFbUID());
                        studentMap.put(student.getFbUID(),student);
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

                    Toast.makeText(updateGrade_levelTwo.this, "-> "+emailList.get(position), Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),updateGrades.class);
                    i.putExtra("grade",grade);
                    i.putExtra("uid",uidList.get(position));
                    i.putExtra("email",emailList.get(position));
                    startActivity(i);

                }
            });
        }

    }
}
