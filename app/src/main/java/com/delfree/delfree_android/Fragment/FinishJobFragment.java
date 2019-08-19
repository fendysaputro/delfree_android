package com.delfree.delfree_android.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.delfree.delfree_android.App;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.SentDataService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.CAMERA_SERVICE;
import static com.delfree.delfree_android.MainActivity.ShowFragment;


/**
 * Created by phephen on 6/8/19.
 */

public class FinishJobFragment extends Fragment {

    Button doneBtn, cameraButton;
    ImageView imageView;
    File photoFile = null;
    App app;
    private static final int CAMERA_REQUEST =123;

    public FinishJobFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish_job, container, false);

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

        app = this.app;
        imageView = (ImageView) view.findViewById(R.id.imView);

        cameraButton = (Button) view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivityForResult(startCamera, CAMERA_REQUEST);
            }
        });

        doneBtn = (Button) view.findViewById(R.id.doneButton);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDoneButton();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    public void onDoneButton(){
        HistoryFragment historyFragment = new HistoryFragment();
        Activity activity = (Activity) getContext();
        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        ShowFragment(R.id.fl_container, historyFragment,fragmentManager);
        getContext().stopService(new Intent(getContext(), SentDataService.class));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
//            app.setImage(photo);
            imageView.setVisibility(View.VISIBLE);
            cameraButton.setEnabled(false);
            cameraButton.setVisibility(View.INVISIBLE);
            doneBtn.setEnabled(true);
        }
    }
}
