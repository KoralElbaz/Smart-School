
package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class menuTeacher extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout Grades ,  Presence , Profile, TestDate, StudentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_teacher);

        Grades=findViewById(R.id.Grades) ;
        Grades.setOnClickListener(this);

        Presence=findViewById(R.id.Presence);
        Presence.setOnClickListener(this);

        Profile= findViewById(R.id.Profile);
        Profile.setOnClickListener(this);

        TestDate = findViewById(R.id.TestDate);
        TestDate.setOnClickListener(this);

        StudentsList = findViewById(R.id.StudentsList);
        StudentsList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Grades:
                startActivity(new Intent(this, gradesTeacher.class));
                break;
            case R.id.Presence:
                startActivity(new Intent(this, presenceTeacher.class));
                break;
            case R.id.Profile:
                startActivity(new Intent(this, profileTeacher.class));
                break;
            case R.id.TestDate:
                startActivity(new Intent(this, TestDate.class));
                break;
            case R.id.StudentsList:
                startActivity(new Intent(this, TeacherStudentsList.class));
                break;

        }
    }
}