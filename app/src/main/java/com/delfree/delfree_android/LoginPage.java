package com.delfree.delfree_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by phephen on 6/8/19.
 */

public class LoginPage extends Activity {

    Button btnLogin;
    EditText edUsername, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        edUsername = (EditText) findViewById(R.id.editTextUsername);
        edPassword = (EditText) findViewById(R.id.editTextPassword);

        btnLogin = (Button) findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnLogin();
            }
        });
    }

    public static void ShowFragment(int resId, Fragment fragment, android.support.v4.app.FragmentManager fm) {
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(resId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void onBtnLogin () {
        Intent intent = new Intent(LoginPage.this, MainActivity.class);
        startActivity(intent);
    }

}
