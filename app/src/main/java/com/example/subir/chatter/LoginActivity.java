package com.example.subir.chatter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    TextView forgotPwd, signUp;
    Button button;
    EditText usr, pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        forgotPwd = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.sign_up);
        button = findViewById(R.id.sign_in);
        usr = findViewById(R.id.username);
        pwd = findViewById(R.id.password);

        forgotPwd.setOnClickListener(this);
        signUp.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.forgot_password:
                //intent = new Intent(LoginActivity.this,ResetPassword.class);
                break;
            case R.id.sign_up:
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.sign_in:
                String username = usr.getText().toString();
                String password = pwd.getText().toString();
                ParseUser.logInInBackground("subir", "123", new LogInCallback() {

                    @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                        alertDisplayer("Sucessful Login","Welcome back " +
                                usr.getText().toString() + "!");

                    } else {
                        ParseUser.logOut();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

                break;
        }
    }


    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(LoginActivity.this, FollowListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

}
