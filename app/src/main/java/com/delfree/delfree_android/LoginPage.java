package com.delfree.delfree_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
                onBtnLogin();
            }
        });

        forgotPassword = findViewById(R.id.tVforgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onForgotPassword();
            }
        });

    }

    private void onBtnLogin () {
        AsyncHttpTask mAuthTask = new AsyncHttpTask("phone="+edPhone.getText()+"&password="+edPassword.getText());
        mAuthTask.execute(AppDelfree.HOST + AppDelfree.LOGIN_PATH, "POST");
        mAuthTask.setHttpResponseListener(new OnHttpResponseListener() {
            @Override
            public void OnHttpResponse(String response) {
                Log.i("batavree", "ini response " + response);
                try {
                    JSONObject resObj = new JSONObject(response);
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
                        Toast.makeText(getApplicationContext(), resObj.getString("m"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void onForgotPassword() {
        Intent intent = new Intent(LoginPage.this, ForgotPasswordPage.class);
        startActivity(intent);
    }

}
