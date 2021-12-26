package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class gradesStudent extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser fUser;
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference Lessons;
    String currID;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_student);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();

        fUser = getIntent().getParcelableExtra("fuser");
        currID = getIntent().getStringExtra("Uid");
        Lessons = database.getReference("Lessons");

        Toast.makeText(gradesStudent.this, "getUid: " + sAuth.getUid(), Toast.LENGTH_LONG).show();

        ArrayList<String> LessonList=new ArrayList<>();
        ArrayList<String> GradeList=new ArrayList<>();
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,LessonList);

        Lessons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.child("students_CourseGrade").hasChild(Objects.requireNonNull(sAuth.getUid()))){
                        Lesson lesson = new Lesson();
                        //LessonList.add(lesson);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
        public void onClick (View v){

        }

}