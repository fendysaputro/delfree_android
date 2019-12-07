package com.delfree.delfree_android.Network;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 *  Created by phephen on 03/09/2019
 */

public class UploadDataTask extends AsyncTask<Object, Void, String> {

    OnHttpResponseListener onHttpResponseListener;

    public void setHttpResponseListener(OnHttpResponseListener listener){
        onHttpResponseListener = listener;
    }

    @Override
    protected String doInBackground(Object... params) {
        String url = (String) params[0];
        String woId = (String) params[1];
        String driverID = (String) params[2];
        String latitude = (String) params[4];
        String longitude = (String) params[5];
        Bitmap bmp = (Bitmap) params[6];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);
        String data = "";

        try {
            HttpMultipartClient client = new HttpMultipartClient(url);
            client.connectForMultipart();
            client.addFormPart("woId", woId);
            client.addFormPart("driverId", driverID);
            client.addFormPart("lat", latitude);
            client.addFormPart("long", longitude);
            client.addFilePart("files", woId, baos.toByteArray());
            client.finishMultipart();
            data = client.getResponse();
            Log.i("Batavree-client", client.toString());
        } catch (Throwable throwable){
            throwable.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        if (onHttpResponseListener != null){
            onHttpResponseListener.OnHttpResponse(result);
        }
    }
}
