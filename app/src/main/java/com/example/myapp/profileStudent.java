package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileStudent extends AppCompatActivity {


    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference ClassRoom;


    private TextView fullName, id, nameSchool, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);


        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        ClassRoom = database.getReference("ClassRoom");
        fullName = findViewById(R.id.fill1);
        id = findViewById(R.id.fill2);
        nameSchool = findViewById(R.id.fill3);
        email = findViewById(R.id.fill4);


        ClassRoom.child(sAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    Student temp = task.getResult().getValue(Student.class);
                    fullName.setText(temp.getFull_name());
                    id.setText((int) temp.getId()+"");
                    nameSchool.setText(temp.get_grade());
                    email.setText(temp.getEmail());
                }
            }
        });

    }
}