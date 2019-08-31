package com.delfree.delfree_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Network.APIService;
import com.delfree.delfree_android.Network.ApiUtils;
import com.delfree.delfree_android.Storage.SharedPrefManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

/**
 * Created by phephen on 6/8/19.
 */

public class LoginPage extends Activity {

    Button btnLogin;
    EditText edPhone, edPassword;
    TextView forgotPassword;
    AppDelfree appDelfree;
    Context context;
    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        appDelfree = (AppDelfree) getApplication();
        context = getApplicationContext();

        edPhone = findViewById(R.id.editTextPhone);
        edPassword = findViewById(R.id.editTextPassword);

        if(SharedPrefManager.getLoggedStatus(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        btnLogin = findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    onBtnLogin(phone, password);
                } else {
                    Toast.makeText(getApplicationContext(), "phone or password can't be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        forgotPassword = findViewById(R.id.tVforgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onForgotPassword();
            }
        });

        mService = ApiUtils.getAPIService();

    }

    private void onBtnLogin (String phone, String password) {
        mService.driverLogin(phone, password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                appDelfree.setLogin(true);
                                SharedPrefManager.setLoggedIn(getApplicationContext(), true);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "phone or password doesn't match", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

    }

    private void onForgotPassword() {
        Intent intent = new Intent(LoginPage.this, ForgotPasswordPage.class);
        startActivity(intent);
    }

}
