package com.delfree.delfree_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Model.Driver;
import com.delfree.delfree_android.Network.APIService;
import com.delfree.delfree_android.Network.ApiUtils;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.HttpHandler;
import com.delfree.delfree_android.Network.OnHttpCancel;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.Network.RetrofitClient;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        btnLogin = findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    onBtnLogin();
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

    private void onBtnLogin(){
        mService.driverLogin(edPhone.getText().toString(), edPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("batavree", "response" + response);
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
