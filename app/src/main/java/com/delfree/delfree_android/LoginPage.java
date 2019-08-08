package com.delfree.delfree_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by phephen on 6/8/19.
 */

public class LoginPage extends Activity {

    TextView tVlogin;
    EditText edUsername, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        edUsername = (EditText) findViewById(R.id.editTextUsername);
        edPassword = (EditText) findViewById(R.id.editTextPassword);

        tVlogin = (TextView) findViewById(R.id.textViewLogin);
        tVlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnLogin();
            }
        });
    }

    private void onBtnLogin () {
        Intent intent = new Intent(LoginPage.this, MainActivity.class);
        startActivity(intent);
    }

}
