package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class presenceStudent extends AppCompatActivity {


    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference ClassRoom;


    private TextView course1, course2, course3, course4, course5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence_student);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        ClassRoom = database.getReference("ClassRoom");
        course1 = findViewById(R.id.fill1);
        course2 = findViewById(R.id.fill2);
        course3 = findViewById(R.id.fill3);
        course4 = findViewById(R.id.fill4);
        course5 = findViewById(R.id.fill5);


        Toast.makeText(presenceStudent.this, "getUid: " + sAuth.getUid(), Toast.LENGTH_LONG).show();
        ClassRoom.child(sAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    Student temp = task.getResult().getValue(Student.class);
                    course1.setText(temp.getPresence().get("Bible").toString());
                    course2.setText(temp.getPresence().get("Computers").toString());
                    course3.setText(temp.getPresence().get("English").toString());
                    course4.setText(temp.getPresence().get("Hebrew").toString());
                    course5.setText(temp.getPresence().get("Math").toString());
                }
            }
        });

    }
}