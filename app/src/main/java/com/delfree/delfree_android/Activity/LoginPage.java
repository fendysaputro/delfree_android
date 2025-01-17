package com.delfree.delfree_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.Driver;
import com.delfree.delfree_android.Network.APIService;
import com.delfree.delfree_android.Network.ApiUtils;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpCancel;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;

import org.json.JSONException;
import org.json.JSONObject;

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

        if(appDelfree.isLogin(LoginPage.this, MainActivity.MODE_PRIVATE)){
            Intent i = new Intent(LoginPage.this, MainActivity.class);
            startActivity(i);
            finish();
        }

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

    private void onBtnLogin () {
        AsyncHttpTask mAuthTask = new AsyncHttpTask("phone="+edPhone.getText()+"&password="+edPassword.getText(), this);
        mAuthTask.setLoginPurpose(true);
        mAuthTask.execute(appDelfree.HOST + appDelfree.LOGIN_PATH, "POST");
        mAuthTask.setHttpResponseListener(new OnHttpResponseListener() {
            @Override
            public void OnHttpResponse(String response) {
                Log.i("batavree", "response " + response);
                try {
                    JSONObject resObj = new JSONObject(response);
                    if (resObj.getBoolean("r")){
                        JSONObject dataObj = resObj.getJSONObject("d");
                        appDelfree.setLogin(true);
                        Driver driver = new Driver(dataObj.getString("_id"),
                                dataObj.getString("name"),
                                dataObj.getString("phone"),
                                dataObj.getString("address"),
                                dataObj.getString("sim_number"),
                                dataObj.getString("sim_expire"),
                                dataObj.getString("token"));
                        appDelfree.setDriver(driver);

                        SharedPreferences sharedPref = getPreferences(getApplication().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("batavree", dataObj.toString());
                        editor.commit();
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        intent.putExtra("fragmentName", 1);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), resObj.getString("m"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

        mAuthTask.setOnHttpCancel(new OnHttpCancel() {
            @Override
            public void OnHttpCancel() {
            }
        });
    }


    private void onForgotPassword() {
        Intent intent = new Intent(LoginPage.this, ForgotPasswordPage.class);
        startActivity(intent);
    }

}
