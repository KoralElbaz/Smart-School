package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.metrics.TrackChangeEvent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TeacherStudentsList extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference Lessons;
    private DatabaseReference ClassRoom;
    private DatabaseReference Teachers;
    String lessonName ;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(TeacherStudentsList.this, " in Create", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_students_list);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();


        Lessons = database.getReference("Lessons");
        Teachers = database.getReference("Teachers");
        ClassRoom = database.getReference("Classroom");

        listView = (ListView) findViewById(R.id.studentslistview);
        Toast.makeText(TeacherStudentsList.this, " after listView", Toast.LENGTH_SHORT).show();

        Teachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(TeacherStudentsList.this, " in DataChange", Toast.LENGTH_SHORT).show();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Toast.makeText(TeacherStudentsList.this, " bool: "+ds.hasChild(Objects.requireNonNull(sAuth.getUid())), Toast.LENGTH_SHORT).show();
                    if(ds.hasChild(Objects.requireNonNull(sAuth.getUid()))){
                        lessonName = ds.getKey();
                        Toast.makeText(TeacherStudentsList.this, "lesson name"+lessonName, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherStudentsList.this, " in cacelled", Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(TeacherStudentsList.this, " after TeachersDataChange"+lessonName, Toast.LENGTH_SHORT).show();

        ArrayList<String> students_names=new ArrayList<>();
        List<Map<String, String>> listArray = new ArrayList<>();
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,students_names);

        Lessons.child(lessonName).child("students_CourseGrade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                       String studentUID = ds.getKey();
                       ClassRoom.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot Csnapshot) {
                               for (DataSnapshot cds: Csnapshot.getChildren()){
                                   if(cds.hasChild(studentUID)){
                                       students_names.add(cds.child(studentUID).child("full_name").getValue().toString());
                                   }

                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                }

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}