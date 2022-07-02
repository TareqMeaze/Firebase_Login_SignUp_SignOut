package com.meaze.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText signUpEmailEditTextId,signUpPasswordEditTextId;
    Button signUpButtonId;
    TextView signInTextViewId;
    ProgressBar progressbarId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up Activity");

        mAuth = FirebaseAuth.getInstance();

        progressbarId=findViewById(R.id.progressbarId);
        signUpEmailEditTextId=findViewById(R.id.signUpEmailEditTextId);
        signUpPasswordEditTextId=findViewById(R.id.signUpPasswordEditTextId);
        signUpButtonId=findViewById(R.id.signUpButtonId);
        signInTextViewId=findViewById(R.id.signInTextViewId);

        signUpButtonId.setOnClickListener(this);
        signInTextViewId.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signUpButtonId:

                userRegister();
                break;

            case R.id.signInTextViewId:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

        }


    }

    private void userRegister(){

        String email = signUpEmailEditTextId.getText().toString().trim();
        String password = signUpPasswordEditTextId.getText().toString().trim();

//===========checking the validity of the email=======================
        if (email.isEmpty()){

            signUpEmailEditTextId.setError("Enter an email address");
            signUpEmailEditTextId.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            signUpEmailEditTextId.setError("Enter a valid a email address");
            signUpEmailEditTextId.requestFocus();
            return;

        }
//=====================================================================


  //============checking the validity of the password======================
        if (password.isEmpty()){

            signUpPasswordEditTextId.setError("Enter a Strong password");
            signUpPasswordEditTextId.requestFocus();
            return;

        }

        if (password.length()<6){

            signUpPasswordEditTextId.setError("Password should be 6 Character");
            signUpPasswordEditTextId.requestFocus();
            return;

        }

        progressbarId.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressbarId.setVisibility(View.GONE);
                if (task.isSuccessful()) {


                    Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),"Register is Successful",Toast.LENGTH_LONG).show();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"This Email Address already Register",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }



            }
        });


    }



}