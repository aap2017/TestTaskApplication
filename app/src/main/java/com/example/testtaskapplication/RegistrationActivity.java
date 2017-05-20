package com.example.testtaskapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    protected boolean validate(EditText editText) {
        String text = editText.getText().toString();
        String errorMessage = "";
        if (text.isEmpty()) {errorMessage = getResources().getString(R.string.empty_mail);} else
            if ((!text.contains("@")) || (text.indexOf("@", (text.indexOf("@"))+1)>0) ||
               (text.indexOf("@")==1) || (text.indexOf("@")==text.length()) || (text.indexOf(".")==1) ||
               (text.indexOf(".")==text.length()) || (text.indexOf(".", text.indexOf("@"))==-1)) {
                errorMessage = getResources().getString(R.string.mail_wrong_format);
            }
        if (!errorMessage.isEmpty()) {
            editText.setError(errorMessage);
        }
        return errorMessage.isEmpty();
    }

    public void registerUser(View view) {
        EditText editText = (EditText) findViewById(R.id.regPassEditText1);
        String pass1 = editText.getText().toString();
        editText = (EditText) findViewById(R.id.regPassEditText2);
        String pass2 = editText.getText().toString();
        editText = (EditText) findViewById(R.id.regEmailEditText);
        String email = editText.getText().toString();
        String errorMessage = "";
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String savedPass = sharedPref.getString(email, "");
        if (!validate(editText)) {
            errorMessage = getResources().getString(R.string.mail_wrong_format);
        } else
        if (!(pass1.equals(pass2))) {
            errorMessage = getResources().getString(R.string.password_equality_error);
        } else
        if (pass1.length()<4) {
            errorMessage = getResources().getString(R.string.password_length_error);
        } else
        if (savedPass.length()!=0) {
            errorMessage = getResources().getString(R.string.user_already_exist);
        };
        if (errorMessage.length()>0) {
            String errorTitle = getResources().getString(R.string.error_dialog_title);
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
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
            //register this user
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(email, pass1);
            editor.commit();
            //exit to main screen
            finish();
        }
    }
}
