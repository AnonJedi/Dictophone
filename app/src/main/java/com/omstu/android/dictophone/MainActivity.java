package com.omstu.android.dictophone;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText username;
    EditText password;
    TextView error;
    Button login;
    SeekBar bar;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        error = (TextView) findViewById(R.id.errorText);
        bar = (SeekBar) findViewById(R.id.seekBar);
        db = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        username.setText("");
        password.setText("");
        error.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                String username = this.username
                        .getText()
                        .toString()
                        .replace(' ', '_');
                String password = this.password
                        .getText()
                        .toString();
                Cursor data = db.get(username);
                if (bar.getProgress() == 0) {
                    String wrongData = "Username and password not match";
                    error.setText(wrongData);
                    if(!data.moveToFirst()) {
                        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                        animation.setDuration(500L);
                        error.startAnimation(animation);
                        error.setVisibility(View.VISIBLE);
                        return;
                    }
                    String DBPass = data.getString(data.getColumnIndex("pass"));
                    password = db.md5(password);
                    if (!password.equals(DBPass)) {
                        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                        animation.setDuration(500L);
                        error.startAnimation(animation);
                        error.setVisibility(View.VISIBLE);
                        return;
                    }
                    error.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(this, MainPageActivity.class);
                    startActivity(intent);
                } else {
                    String userExist = "User with this name already exist";
                    error.setText(userExist);
                    if(data.moveToFirst()) {
                        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                        animation.setDuration(500L);
                        error.startAnimation(animation);
                        error.setVisibility(View.VISIBLE);
                        return;
                    }
                    db.insert(username, password);
                    this.username.setText("");
                    this.password.setText("");
                }
                break;
        }
    }
}
