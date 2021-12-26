package com.example.myapp;

import android.os.Bundle;
import android.view.View;
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

public class gradesStudent extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser fUser;
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;
    private DatabaseReference ClassRoom;
    String currID;

    private TextView course1, course2, course3, course4, course5;
    //private List<String> courses = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_student);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();

        fUser = getIntent().getParcelableExtra("fuser");
        currID = getIntent().getStringExtra("Uid");
        ClassRoom = database.getReference("ClassRoom");
        course1 = findViewById(R.id.fill1);
        course2 = findViewById(R.id.fill2);
        course3 = findViewById(R.id.fill3);
        course4 = findViewById(R.id.fill4);
        course5 = findViewById(R.id.fill5);



        Toast.makeText(gradesStudent.this, "getUid: " + sAuth.getUid(), Toast.LENGTH_LONG).show();
        ClassRoom.child(sAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    Student temp = task.getResult().getValue(Student.class);
                    course1.setText(temp.getCourses_with_grade().get("Bible").toString());
                    course2.setText(temp.getCourses_with_grade().get("Computers").toString());
                    course3.setText(temp.getCourses_with_grade().get("English").toString());
                    course4.setText(temp.getCourses_with_grade().get("Hebrew").toString());
                    course5.setText(temp.getCourses_with_grade().get("Math").toString());

                }
            }


        });

    }

    @Override
    public void onClick(View v) {

    }
}