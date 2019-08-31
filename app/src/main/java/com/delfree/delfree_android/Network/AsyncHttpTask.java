package com.delfree.delfree_android.Network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Phephen on 31/08/2019
 */

public class AsyncHttpTask extends AsyncTask<String, Void, String>{

    OnHttpResponseListener onHttpResponseListener;
    OnHttpCancel onHttpCancel;
    String data;

    public void setHttpResponseListener(OnHttpResponseListener listener) {
        this.onHttpResponseListener = listener;
    }

    public void setOnHttpCancel(OnHttpCancel onHttpCancel){
        this.onHttpCancel = onHttpCancel;
    }

    public AsyncHttpTask(String data){
        this.data = data;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuffer response = null;
        try {
            String url = params[0];
            String method = params[1];
            URL obj = new URL(url);
            Log.d("tracking", url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            if (method.equals("POST")) {
                con.setRequestProperty("Content-Type", "application/z-www-form-urlencoded;charset=UTF-8");
                con.setRequestProperty("Content-Lenght", "" + Integer.toString(data.getBytes().length));
                con.setUseCaches(false);
                con.setDoInput(true);
                con.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
            }
            con.connect();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
            }
            con.disconnect();
        } catch (Exception e){
            Log.d("tracking", "error AsyncHTTPTASK: "+e.getMessage());
        }

        if (response != null){
            return response.toString();
        } else {
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (onHttpResponseListener != null){
            onHttpResponseListener.OnHttpResponse(result);
        }
    }

    @Override
    protected void onCancelled() {
        if (onHttpCancel != null){
            onHttpCancel.OnHttpCancel();
        }
    }
}