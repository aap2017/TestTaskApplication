package com.example.testtaskapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.testtaskapplication.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Context context = getApplicationContext();
        SharedPreferences sessionSharedPref = context.getSharedPreferences(getString(R.string.save_session_file_key), Context.MODE_PRIVATE);
        String savedSession = sessionSharedPref.getString("session", "");
        if (savedSession.length()!=0) {
            Intent intent = new Intent(this, EnterActivity.class);
            intent.putExtra(EXTRA_MESSAGE, savedSession);
            startActivity(intent);
        }
    }

    public void registration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        EditText editText = (EditText) findViewById(R.id.emailEditText);
        String email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.passwordEditText);
        String password = editText.getText().toString();
        if (!userCheck(email, password)) {
            String errorTitle = getResources().getString(R.string.error_dialog_title);
            String errorMessage = getResources().getString(R.string.unknown_user_password);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(errorTitle);
            builder.setMessage(errorMessage);
            builder.setCancelable(false);
            builder.setNegativeButton("ОК", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            Context context = getApplicationContext();
            SharedPreferences sessionSharedPref = context.getSharedPreferences(getString(R.string.save_session_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor sessionEditor = sessionSharedPref.edit();
            sessionEditor.putString("session", email);
            sessionEditor.commit();

            Intent intent = new Intent(this, EnterActivity.class);
            intent.putExtra(EXTRA_MESSAGE, email);
            startActivity(intent);
        }
    }

    public boolean userCheck(String email, String password) {
        if (email.length()<6 || password.length()<4) return false;
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return (sharedPref.getString(email, "").equals(password));
    }
}
