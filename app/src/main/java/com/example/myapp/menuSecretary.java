package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class menuSecretary extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout registration1 ,  registration2 , list , placement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_secretary);

        registration1=findViewById(R.id.registration1) ;
        registration1.setOnClickListener(this);

        registration2=findViewById(R.id.registration2);
        registration2.setOnClickListener(this);

        list= findViewById(R.id.list);
        list.setOnClickListener(this);

        placement= findViewById(R.id.placement);
        placement.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registration1:
                Toast.makeText(menuSecretary.this, "1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, StudentRegistration.class));
                break;
            case R.id.registration2:
                Toast.makeText(menuSecretary.this, "2", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, TeacherRegistration.class));
                break;
            case R.id.list:
                Toast.makeText(menuSecretary.this, "3", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, studentList.class));
                break;
            case R.id.placement:
                Toast.makeText(menuSecretary.this, "4", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PlacementStudent.class));
                break;
        }
    }
}