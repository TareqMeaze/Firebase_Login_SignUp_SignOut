package com.meaze.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText signInEmailEditTextId,signInPasswordEditTextId;
    Button signInButtonId;
    TextView signUpTextViewId;
    private FirebaseAuth mAuth;
    ProgressBar progressbarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In Activity");

        mAuth = FirebaseAuth.getInstance();

        signInEmailEditTextId=findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditTextId=findViewById(R.id.signInPasswordEditTextId);
        signInButtonId=findViewById(R.id.signInButtonId);
        signUpTextViewId=findViewById(R.id.signUpTextViewId);
        progressbarId=findViewById(R.id.progressbarId);

        signInButtonId.setOnClickListener(this);
        signUpTextViewId.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signInButtonId:

                loginInfo();
                break;

            case R.id.signUpTextViewId:
                Intent myIntent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(myIntent);
                break;


        }


    }

    private void loginInfo(){

        String lEmail =signInEmailEditTextId.getText().toString().trim();
        String lPassword=signInPasswordEditTextId.getText().toString().trim();

//======== checking the validity of the email ============================
        if (lEmail.isEmpty()){

            signInEmailEditTextId.setError("Enter an email address");
            signInEmailEditTextId.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(lEmail).matches()){

            signInPasswordEditTextId.setError("Enter a valid Email Address");
            signInPasswordEditTextId.requestFocus();
            return;

        }
  //====================================================================

  //========  checking the validity of the password==================

        if (lPassword.isEmpty()){

            signInPasswordEditTextId.setError("Enter a strong password");
            signInPasswordEditTextId.requestFocus();
            return;
        }

        if (lPassword.length()<6){

            signInPasswordEditTextId.setError("Password Should be 6 character");
            signInPasswordEditTextId.requestFocus();
            return;

        }

//================================================

        progressbarId.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(lEmail,lPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressbarId.setVisibility(View.GONE);
                if (task.isSuccessful()){


                    Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Login UnSuccessful",Toast.LENGTH_LONG).show();
                }


            }
        });

    }


}