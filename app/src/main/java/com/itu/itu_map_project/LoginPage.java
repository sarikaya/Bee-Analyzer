package com.itu.itu_map_project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class LoginPage extends ActionBarActivity implements DBTransaction.AsyncLoginResponse {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                EditText user = (EditText) findViewById(R.id.usernameText);
                EditText pass = (EditText) findViewById(R.id.passwordText);
                String username = user.getText().toString();
                String password = pass.getText().toString();
                JSONObject result;
                if (((username != null || !username.isEmpty()) && (password != null || !password.isEmpty()))) {
                    DBTransaction.AsyncLogin tryLogin = new DBTransaction.AsyncLogin();
                    tryLogin.delegate = this;
                    tryLogin.execute(username, password);
                }
                break;
        }
    }

    @Override
    public void processFinish(boolean result) {
        try{
            if(result) {
                Intent startAdminActivity = new Intent(this, AdminPage.class);
                startActivity(startAdminActivity);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
