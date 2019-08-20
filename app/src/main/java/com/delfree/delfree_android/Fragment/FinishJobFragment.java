package com.delfree.delfree_android.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.SentDataService;

import static com.delfree.delfree_android.MainActivity.ShowFragment;


/**
 * Created by phephen on 6/8/19.
 */

public class FinishJobFragment extends Fragment {

    Button doneBtn, cameraButton;
    ImageView imageView;
    AppDelfree appDelfree;
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

        appDelfree = (AppDelfree) getActivity().getApplicationContext();
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
        if (appDelfree.isPicture()){
            ShowFragment(R.id.fl_container, historyFragment,fragmentManager);
            getContext().stopService(new Intent(getContext(), SentDataService.class));
        } else {
            Toast.makeText(this.getContext(), "You must take picture later", Toast.LENGTH_LONG).show();
        }
    }

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
}
