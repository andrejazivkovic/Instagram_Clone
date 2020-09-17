package com.example.parseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    public void showUserList(){

        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            clicked(view);
        }
        return false;
    }

    boolean flag = true;
    @Override
    public void onClick(View view) {
        buttonOfChange= (Button) findViewById(R.id.buttonOfChange);
        or= (TextView) findViewById(R.id.changeText);

        if(view.getId() == R.id.changeText){
           if(flag){
               flag = false;
               buttonOfChange.setText("Log In");
               or.setText("or, Sign Up");
           }
           else {
               flag = true;
               buttonOfChange.setText("Sign Up");
               or.setText("or, Log In");
           }
        }else if (view.getId() == R.id.signUp || view.getId() == R.id.imageView){
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

            }

        }


    Button buttonOfChange;

    TextView or;
    EditText password;
    EditText username;



    public void clicked(View view){
        buttonOfChange = (Button) findViewById(R.id.buttonOfChange);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        if(username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(getApplicationContext(),"Username and Password are required", Toast.LENGTH_SHORT).show();
        }
        else {
            if (buttonOfChange.getText().toString().matches("Sign Up")) {
                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Successful Sign UP", Toast.LENGTH_SHORT).show();
                            showUserList();

                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else if(buttonOfChange.getText().toString().matches("Log In")){
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e == null && user !=null){
                            Toast.makeText(getApplicationContext(), "Successful Log In",Toast.LENGTH_SHORT).show();
                            showUserList();
                        }else {
                            assert e != null;
                            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        or= (TextView) findViewById(R.id.changeText);
        or.setOnClickListener(this );
        password= (EditText) findViewById(R.id.password);
        password.setOnKeyListener(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.signUp);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

         constraintLayout.setOnClickListener(this);
        imageView.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null ){
            showUserList();
        }



        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}