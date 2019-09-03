package com.delfree.delfree_android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.delfree.delfree_android.R;

public class ForgotPasswordPage extends Activity {

    Button btnSave;
    EditText edPhone, edNewPassword, edConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpasswordpage);

        edPhone = (EditText) findViewById(R.id.editTextPhone);
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
        Intent intent = new Intent(ForgotPasswordPage.this, LoginPage.class);
        startActivity(intent);
    }
}
