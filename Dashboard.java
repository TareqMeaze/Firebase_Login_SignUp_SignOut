package com.meaze.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth=FirebaseAuth.getInstance();

    }
//========= Inflate menu XML===================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }
  //===========================================

    //================ Click Sign Out option =================


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

        return super.onOptionsItemSelected(item);
    }
  //======================================================

}