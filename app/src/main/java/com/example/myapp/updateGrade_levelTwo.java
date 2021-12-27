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
        setContentView(R.layout.activity_update_grades_level2);

        Toast.makeText(updateGrade_levelTwo.this, "create", Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance();
        listView=(ListView) findViewById(R.id.students_grade_list);
        Teachers = database.getReference("Teachers");
        Lessons = database.getReference("Lessons");
        sAuth = FirebaseAuth.getInstance();
        student=new Student();

        Teachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(updateGrade_levelTwo.this, "inside sanpshot: " , Toast.LENGTH_SHORT).show();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Toast.makeText(updateGrade_levelTwo.this, "inside sanpshot: "+ds.getKey() , Toast.LENGTH_SHORT).show();
                    if(ds.hasChild(Objects.requireNonNull(sAuth.getUid()))){
                        Toast.makeText(updateGrade_levelTwo.this, " insideIf: "+ds.getKey() , Toast.LENGTH_SHORT).show();
                        lessonName=ds.getKey();
                        Toast.makeText(updateGrade_levelTwo.this, "-> "+lessonName, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(updateGrade_levelTwo.this, " lessonName: "+lessonName , Toast.LENGTH_SHORT).show();

        ArrayList<String> names=new ArrayList<>();
        ArrayList<String> uidList=new ArrayList<>();


        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);

        Intent intent = this.getIntent();

        if(intent!=null)
        {
            String grade = intent.getStringExtra("grade");
            Lessons.child(lessonName).child("students_CourseGrade").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds :snapshot.getChildren()){
                        Toast.makeText(updateGrade_levelTwo.this, " in DataChange" , Toast.LENGTH_SHORT).show();
                        student = ds.getValue(Student.class);
                       if(student.get_grade().equals(grade)){
                           Toast.makeText(updateGrade_levelTwo.this, " inside if "+ student.get_grade(), Toast.LENGTH_SHORT).show();
                           names.add(student.getFull_name());
                           Toast.makeText(updateGrade_levelTwo.this, "-> "+student.getFull_name(), Toast.LENGTH_SHORT).show();
                           uidList.add(student.getFbUID());

                       }
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

                    Toast.makeText(updateGrade_levelTwo.this, "-> "+uidList.get(position), Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),updateGrades.class);
                    i.putExtra("grade",grade);
                    i.putExtra("uid",uidList.get(position));
                    i.putExtra("email",uidList.get(position));
                    startActivity(i);

                }
            });
        }

    }
}
