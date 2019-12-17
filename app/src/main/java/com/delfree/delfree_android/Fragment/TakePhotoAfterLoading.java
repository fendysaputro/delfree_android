package com.delfree.delfree_android.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.Network.UploadDataTask;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

/**
 * created by phephen 2019
 */
public class TakePhotoAfterLoading extends Fragment {
    Button doneBtn, cameraButton;
    ImageView imageView;
    AppDelfree appDelfree;
    private static final int CAMERA_REQUEST = 1;
    private AppDataService appDataService;
    private Context context;
    Intent mServiceIntent;
    File photoFile = null;
    String fileName = "";

    public TakePhotoAfterLoading() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_photo_after_loading, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Back");
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().onBackPressed();
            }
        });

        context = getContext();
        requestPermission();

        appDelfree = (AppDelfree) getActivity().getApplicationContext();
        imageView = (ImageView) view.findViewById(R.id.imView);

        cameraButton = (Button) view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(startCamera, CAMERA_REQUEST);
            }
        });

        doneBtn = (Button) view.findViewById(R.id.startButton);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDoneButton();
            }
        });

        return view;
    }

    private void requestPermission (){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 6);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 7);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.allowBackPressed = false;
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    public void onDoneButton(){
        UploadDataTask uploadTask = new UploadDataTask();
        String wo_No = "Wo_No";
//        uploadTask.execute(appDelfree.HOST + appDelfree.UPLOAD_PATH + date + appDelfree.getImage() +
//                appDelfree.getDriver().getName() + appDelfree.getLatitude() + appDelfree.getLongitude());
//        uploadTask.setHttpResponseListener(new OnHttpResponseListener() {
//            @Override
//            public void OnHttpResponse(String result) {
        if (appDelfree.isPicture()){
            dialog();
        } else {
            Toast.makeText(getContext(), "You must take picture first", Toast.LENGTH_LONG).show();
        }
    }
//        });
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            appDelfree.setImage(photo);
            appDelfree.setPicture(true);
            imageView.setVisibility(View.VISIBLE);
            cameraButton.setEnabled(false);
            cameraButton.setVisibility(View.INVISIBLE);
            doneBtn.setEnabled(true);
        }
    }

    public void dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Mulai Perjalanan");
        alertDialog.setMessage("Apakah anda yakin memulai perjalanan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        AsyncHttpTask startTask = new AsyncHttpTask("woid=" + woId +
//                                "&driverid=" + driverId +
//                                "&vehicleid=" + vehicleId +
//                                "&lang=" + latitude +
//                                "&long=" + longitude, getContext());
//                        startTask.execute(appDelfree.HOST + appDelfree.START_PATH, "POST");
//                        startTask.setHttpResponseListener(new OnHttpResponseListener() {
//                            @Override
//                            public void OnHttpResponse(String response) {
//                                try {
//                                    JSONObject resWo = new JSONObject(response);
//                                    if (resWo.getBoolean("r")){
//                                        Toast.makeText(getActivity(), resWo.getString("m"), Toast.LENGTH_LONG).show();
////                                        resWo.getJSONObject("d");
//                                        Log.i("batavree", "loading fragment " + resWo.getJSONObject("d").toString());
//                                        appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).setStatus(resWo.getJSONObject("d").getString("status"));
//                                        status.setText("Status : " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getStatus());
//                                    }
//                                } catch (JSONException jss){
//                                    Log.e("batavree", jss.getMessage());
//                                }
//                            }
//                        });
//                        ProgressRouteFragment progressRouteFragment = new ProgressRouteFragment();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fl_container, progressRouteFragment);
//                        fragmentTransaction.commit();
                        context = getContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startService(new Intent(context, AppDataService.class));
                        context.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }
}
