package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class menuStudent extends AppCompatActivity implements View.OnClickListener{


    private LinearLayout Grades ,  Presence , Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_student);


        Grades=findViewById(R.id.Grades) ;
        Grades.setOnClickListener(this);

        Presence=findViewById(R.id.Presence);
        Presence.setOnClickListener(this);

        Profile= findViewById(R.id.Profile);
        Profile.setOnClickListener(this);

        Profile= findViewById(R.id.Lessons);
        Profile.setOnClickListener(this);



    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Grades:
                Intent i = new Intent(getApplicationContext(),gradesStudent.class);
                startActivity(i);
                break;
            case R.id.Presence:
                Intent j = new Intent(getApplicationContext(),presenceStudent.class);
                startActivity(j);
                break;
            case R.id.Profile:
                startActivity(new Intent(this, profileStudent.class));
                break;
            case  R.id.Lessons:
                startActivity(new Intent(this, studentLessonsList.class));
                break;
        }

    }


}