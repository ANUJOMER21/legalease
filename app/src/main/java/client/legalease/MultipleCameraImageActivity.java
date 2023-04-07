package client.legalease;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.cameraview.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import client.legalease.Common.FileUtils;
import client.legalease.Model.UploadBillModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.CallApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MultipleCameraImageActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    ProgressDialog pDialog;


    ArrayList<String> listCaptureImage;
    LinearLayout llImage;
    private CameraView mCameraView;
    private Handler mBackgroundHandler;
    private String TAG = "MainActivity";


    CommonSharedPreference commonSharedPreference;
    String myToken ="";
    String id = "";
    CallApi callApi = new CallApi();
    String categorySelector = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_camera_image);
        mCameraView = (CameraView) findViewById(R.id.camera);
        llImage = findViewById(R.id.ll_main_image);
        listCaptureImage = new ArrayList<>();
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }

        try {
            Intent intent  =getIntent();
            categorySelector  =intent.getStringExtra("categorySelector");
        }catch (NullPointerException ignored){

        }


        commonSharedPreference = new CommonSharedPreference(getApplicationContext());
        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                myToken = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                id = String.valueOf(commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId());
            } else {
                myToken = "";
                id = "";
            }
        } catch (NullPointerException ignored) {

        }

        Button send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("af", String.valueOf(listCaptureImage));
                uploadFiles(listCaptureImage);


            }
        });
    }



    private CameraView.Callback mCallback
            = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d(TAG, "onCameraOpened");
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.d(TAG, "onPictureTaken " + data.length);
            Toast.makeText(cameraView.getContext(), "Picture taken", Toast.LENGTH_SHORT)
                    .show();
            getBackgroundHandler().post(new Runnable() {
                @Override
                public void run() {
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            System.currentTimeMillis() + "_picture.jpg");
                    try (OutputStream os = new FileOutputStream(file)) {
                        os.write(data);
                        os.close();

                        listCaptureImage.add(file.getPath());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showCaptureImage();
                            }
                        });
                    } catch (IOException e) {
                        Log.w(TAG, "Cannot write to " + file, e);
                    }
                }
            });
        }
    };

    private void showCaptureImage() {
        if (listCaptureImage.size() > 0) {
            llImage.removeAllViews();
            for (int i = 0; i < listCaptureImage.size();
                 i++) {
                final String strPath = listCaptureImage.get(i);
                View view = LayoutInflater.from(MultipleCameraImageActivity.this)
                        .inflate(R.layout.item_image_capture, null, false);
                ImageButton imageButton = view.findViewById(R.id.ib_item_capture_clear);
                imageButton.setTag(i);
                final int finalI = i;
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listCaptureImage.remove(finalI);
                        showCaptureImage();
                    }
                });
                ImageView ivImage = view.findViewById(R.id.iv_capture_image);
                Glide.with(this).load(strPath).into(ivImage);
                llImage.addView(view);
            }
        }
    }


    public void onCaptureImage(View view) {
        if (mCameraView != null) {
            mCameraView.takePicture();
        }
    }





    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            mCameraView.start();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        }
    }

    @Override
    protected void onPause() {
        mCameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            mBackgroundHandler.getLooper().quitSafely();
            mBackgroundHandler = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (permissions.length != 1 || grantResults.length != 1) {
                    throw new RuntimeException("Error on requesting camera permission.");
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Not granted",
                            Toast.LENGTH_SHORT).show();
                }
                // No need to start camera here; it is handled by onResume
                break;
        }
    }

    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }



    private void uploadFiles(ArrayList<String> listCaptureImage) {


        pDialog = new ProgressDialog(MultipleCameraImageActivity.this);
        String a ="";
        try {
            a = String.valueOf(listCaptureImage.size());
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignored){

        }
        Log.d("bh", String.valueOf(listCaptureImage));
        String pleaseWait = "";
        if (a.equals("1")){
            pleaseWait = "Please wait... your "+a+" file is getting uploaded";
        }else {
            pleaseWait = "Please wait... your " + a + " files are getting uploaded";
        }
        pDialog.setMessage(pleaseWait);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        List<MultipartBody.Part> filex=new ArrayList<>();
        try {
            for (int i=0;i<listCaptureImage.size();i++){
                filex.add(prepareFilePart("filex[]", Uri.parse(listCaptureImage.get(i))));

            }
            RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), categorySelector);

            RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), id);


            callApi.uploadBillCamera(MultipleCameraImageActivity.this,filex,userId,type);
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignored){

        }
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
//        File file = FileUtils.getFile(this, fileUri);
        File file =new File(fileUri.getPath());

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    public void Response_uploadBill(UploadBillModel body) {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "File uploaded SuccesFully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MultipleCameraImageActivity.this,BillUploadActivty.class);
        startActivity(intent);
        finish();
    }

    public void checkMessage() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Something went wrong... please try after sometimes", Toast.LENGTH_SHORT).show();
    }


}
