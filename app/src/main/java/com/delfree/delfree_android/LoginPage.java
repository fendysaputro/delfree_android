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
import com.delfree.delfree_android.Network.OnHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

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

        appDelfree = (AppDelfree) getBaseContext().getApplicationContext();
        context = getApplicationContext();

        edPhone = findViewById(R.id.editTextPhone);
        edPassword = findViewById(R.id.editTextPassword);

        btnLogin = findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                onBtnLogin(edPhone.getText().toString().trim(), edPassword.getText().toString().trim());
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
                .enqueue(new Callback<Driver>() {
                    @Override
                    public void onResponse(Call<Driver> call, Response<Driver> res) {
                        Log.i("batavree", "ini response" + res);
                        if (res.isSuccessful()){
                            JSONObject resObj = new JSONObject();
                            Log.i("batavree", "ini json" + resObj);
                            try {

                                if (resObj.getBoolean("r")){
                                    JSONObject dataObj = new JSONObject("d");
                                    appDelfree.setLogin(true);
                                    Driver driver = new Driver(dataObj.getString("name"), dataObj.getString("phone"), dataObj.getString("address"),
                                            dataObj.getString("simNumber"), dataObj.getString("simExpired"), dataObj.getString("token"));
                                    appDelfree.setDriver(driver);

                                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("batavree", 0);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("batavree", dataObj.toString());
                                    editor.commit();
                                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), "ini error", Toast.LENGTH_LONG).show();
                            }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Driver> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void onForgotPassword() {
        Intent intent = new Intent(LoginPage.this, ForgotPasswordPage.class);
        startActivity(intent);
    }

}
