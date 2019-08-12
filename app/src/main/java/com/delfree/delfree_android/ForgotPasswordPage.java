package com.delfree.delfree_android;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordPage extends Activity {

    Button btnSave;
    EditText edUsername, edNewPassword, edConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpasswordpage);
        
        edUsername = (EditText) findViewById(R.id.editTextUsername);
        edNewPassword = (EditText) findViewById(R.id.eTNewPassword);
        edConfirmPassword = (EditText) findViewById(R.id.eTConfirmPassword);

        btnSave = (Button) findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePassword();
            }
        });
    }

    private void savePassword() {
        Toast.makeText(getApplicationContext(), "this is save new password", Toast.LENGTH_LONG).show();
    }
}
