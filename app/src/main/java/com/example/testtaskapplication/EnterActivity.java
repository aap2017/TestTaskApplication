package com.example.testtaskapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(message);
    }

    public void exit(View view){
        Context context = getApplicationContext();
        SharedPreferences sessionSharedPref = context.getSharedPreferences(getString(R.string.save_session_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sessionEditor = sessionSharedPref.edit();
        sessionEditor.remove("session");
        sessionEditor.commit();

        finish();
    }
}
