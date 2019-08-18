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

    public FinishJobFragment() {
        // Required empty public constructor
    }

//    private void setImagePreview(){
//        Bitmap bmp = BitmapFactory.decodeFile(app.getImageFile().getPath());
//        imageView.setImageBitmap(bmp);
//        imageView.setVisibility(View.VISIBLE);
//        doneBtn.setEnabled(false);
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish_job, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Back");
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));
        toolbar.setNavigationIcon(R.drawable.back);

        app = this.app;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        cameraButton = (Button) view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
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
        ShowFragment(R.id.fl_container, historyFragment, getFragmentManager());
    }

//    private void requestPermission (){
//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET)
//                == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, 5);
//        }
//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 6);
//        }
//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 7);
//        }
//
//        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.RECORD_AUDIO)
//                == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.RECORD_AUDIO}, 8);
//        }
//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
//        }
//    }

//    private void takePhoto(String mediaType, int codeRequest){
//        Intent takeMediaIntent = new Intent(mediaType);
//        if (takeMediaIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            try {
//                if (codeRequest == REQUEST_IMAGE_CAPTURE) {
//                    photoFile = createImageFile();
//                    Log.i("batavree", "fotofile " + photoFile.getAbsolutePath());
//                    if (photoFile != null) {
//                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
//                                "com.delfree.delfree_android",
//                                photoFile);
//                        Log.i("batavree", "fotofileUri " + photoURI.getPath());
////                        Uri photoURI = Uri.fromFile(photoFile);
//                        takeMediaIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        startActivityForResult(takeMediaIntent, codeRequest);
//                        Log.i("batavree", photoFile.getName());
//                    }
//                }
//            } catch (IOException ex) {
//                Log.e("amg", ex.getMessage());
//            }
//        }
//    }
//
//    String mCurrentPhotoPath;
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Bitmap bp = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bp);
            app.setImage(bp);
            doneBtn.setEnabled(true);

    }
}
