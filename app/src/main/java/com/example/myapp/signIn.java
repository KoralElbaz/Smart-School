package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signIn extends AppCompatActivity implements View.OnClickListener {

    private TextView  signInBtn;

    private FirebaseUser fUser;
    private FirebaseDatabase database;
    private FirebaseAuth sAuth;

    private DatabaseReference ClassRoom;
    private DatabaseReference Teachers;
    private DatabaseReference Secretariat;

    private EditText  Email , Password ;
    private String email , pass ;
    private boolean isTeacher, value1 , value2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        database = FirebaseDatabase.getInstance();
        sAuth = FirebaseAuth.getInstance();
        fUser = sAuth.getCurrentUser();

        signInBtn=(Button) findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(this);


        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.PasswordTeacher);
        isTeacher=false;


        Secretariat=database.getReference("Secretariat");
        ClassRoom = database.getReference("ClassRoom");
        Teachers = database.getReference("Teachers");


    }

    @Override
    public void onClick(View v) {
        email = Email.getText().toString();
        pass = Password.getText().toString();

        switch (v.getId())
        {
            case R.id.signInBtn:
                if(email.equals("sivan@gmail.com")|| email.equals("yoel@gmail.com") || email.equals("koral@gmail.com"))
                {
                    loginSecretariat();
                }
                else
                    { loginOther(); }
                break;
        }
    }


    private void loginOther()
    {
        Teachers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot ds: snapshot.getChildren()){
                    if (ds.hasChild(fUser.getUid()))
                        isTeacher = true;
                }
                if (isTeacher) // is a teacher
                {
                    sAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(signIn.this, "Login Teachers Successfully " , Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(),menuTeacher.class);
                                startActivity(i);
                            }
                            else
                                {
                                    Toast.makeText(signIn.this, "Login Teachers Unsuccessfully - Go to the Secretary  " , Toast.LENGTH_LONG).show();
                                }
                        }
                    });
                }
                else // is a student
                {
                    sAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(signIn.this, "Login Student Successfully " , Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(),menuStudent.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(signIn.this, "Login Student Unsuccessfully - Go to the Secretary" , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }

    private void loginSecretariat()
    {
        sAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Secretary temp = new Secretary(fUser.getUid(),email,pass);
                if (task.isSuccessful())
                {
                    Toast.makeText(signIn.this, "Login Secretariat Successfully " , Toast.LENGTH_LONG).show();
                    Secretariat.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if (!snapshot.hasChild(fUser.getUid()))
                            {
                                Secretariat.child(fUser.getUid()).setValue(temp);
                                Secretariat.child(fUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(signIn.this, "Welcome ", Toast.LENGTH_LONG).show();

                                            Intent i = new Intent(getApplicationContext(),menuSecretary.class);
                                            startActivity(i);
                                        }
                                    }
                                });
                            }
                            else
                                {
                                    Secretariat.child(fUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(signIn.this, "Welcome Back" , Toast.LENGTH_LONG).show();

                                                Intent i = new Intent(getApplicationContext(),menuSecretary.class);
                                                startActivity(i);
                                            }
                                        }
                                    });

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {  }
                    });
                }
                else
                {
                    Toast.makeText(signIn.this, "Login Unsuccessfully", Toast.LENGTH_LONG).show();
                }
            }

        });
        database.getReference();
    }
}