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

public class profileTeacher extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference Teachers;


    private TextView fullName, id, nameSchool, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        Teachers = database.getReference("Teachers");
        fullName = findViewById(R.id.fill1);
        id = findViewById(R.id.fill2);
        nameSchool = findViewById(R.id.fill3);
        email = findViewById(R.id.fill4);


        Toast.makeText(profileTeacher.this, "getUid: " + sAuth.getUid(), Toast.LENGTH_LONG).show();
        Teachers.child(sAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    Teacher temp = task.getResult().getValue(Teacher.class);
                    fullName.setText(temp.getFull_name());
                    id.setText((int) temp.getId()+"");
                    nameSchool.setText(temp.getName_lesson());
                    email.setText(temp.getEmail());
                }
            }
        });

    }
}