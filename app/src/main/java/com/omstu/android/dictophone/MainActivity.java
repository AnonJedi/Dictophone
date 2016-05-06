package com.omstu.android.dictophone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText username;
    EditText password;
    TextView error;
    Button login;
    SeekBar bar;

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
                if (username.equals("admin") && password.equals("123")) {
                    error.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(this, MainPageActivity.class);
                    startActivity(intent);
                } else {
                    AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(500L);
                    error.startAnimation(animation);
                    error.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
