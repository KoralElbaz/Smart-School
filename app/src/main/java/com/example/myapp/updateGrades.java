package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class updateGrades extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ClassRoom;

    private Button updateBtn;
    private TextView fullName, id, email ;
    private EditText course1, course2, course3, course4, course5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grades);

        database = FirebaseDatabase.getInstance();
        ClassRoom = database.getReference("ClassRoom");

        updateBtn=findViewById(R.id.updateBtn);
        fullName = findViewById(R.id.fill1);
        id = findViewById(R.id.fill2);
        email = findViewById(R.id.fill3);
        course1 = findViewById(R.id.fill4);
        course2 = findViewById(R.id.fill5);
        course3 = findViewById(R.id.fill6);
        course4 = findViewById(R.id.fill7);
        course5 = findViewById(R.id.fill8);

        Intent intent = this.getIntent();

        if(intent!=null)
        {
            String email2 = intent.getStringExtra("email");
            String full_name = intent.getStringExtra("full_name");
            String id2 = intent.getStringExtra("id");
            String uid = intent.getStringExtra("uid");

            fullName.setText(full_name);
            id.setText(id2);
            email.setText(email2);

            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input1=course1.getText().toString();
                    String input2=course2.getText().toString();
                    String input3=course3.getText().toString();
                    String input4=course4.getText().toString();
                    String input5=course5.getText().toString();

//                     ClassRoom.child(uid).child("courses_with_grade").child("Bible").setValue(input1);

                    HashMap hashMap=new HashMap();
                    if(!input1.equals("")){ hashMap.put("Bible",input1);}
                    if(!input2.equals("")){ hashMap.put("Computers",input2);}
                    if(!input3.equals("")){hashMap.put("English",input3); }
                    if(!input4.equals("")){hashMap.put("Hebrew",input4);}
                    if(!input5.equals("")){hashMap.put("Math",input5);}

                    ClassRoom.child(uid).child("courses_with_grade").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            ClassRoom.child(uid).child("update_grade").setValue(true);
                            Toast.makeText(updateGrades.this, "Update Successfully", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });

        }



    }
}