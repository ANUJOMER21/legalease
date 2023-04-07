package client.legalease;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.legalease.Adapter.AdapterDocument;
import client.legalease.Adapter.AdapterUploadedDocument;
import client.legalease.Common.ImagePickerActivity;
import client.legalease.Interface.IImageCompressTaskListener;
import client.legalease.Model.DeleteDocModel.DeleteDocModel;
import client.legalease.Model.UploadDocModel.UploadDocModel;
import client.legalease.Model.UploadDocumentSpinnerModel.UploadDocumentSpinnerModel;
import client.legalease.Model.UploadDocumentSpinnerModel.UploadSpinnerModel;
import client.legalease.Model.UploadedDocumentModel.UploadedData;
import client.legalease.Model.UploadedDocumentModel.UploadedDocumentModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.Utilities.ImageCompressTask;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UploadDocument extends AppCompatActivity {
    List<UploadSpinnerModel> spinnerData = new ArrayList<>();

    Spinner spinner;
    ArrayList<String> arrayList = new ArrayList<>();
    HashMap<String,String> myDocumentList =null;
    ArrayList<String> documentId =null;
    ArrayList<String> documentTypeId =null;
    String documentSelectedValue = "";

    ArrayList<String> documentName =null;
    String selectedDocumentId= "";
    ImageView iv_pan;
    ProgressDialog pDialog;
    CallApi callApi  =new CallApi();
    Dialog dialog;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int STORAGE_PERMISSION_CONSTANT = 101;
    public static final int REQUEST_IMAGE = 100;
    ProgressDialog progressDialog;
    boolean boolean_save;
    CommonSharedPreference commonSharedPreference;
    String myToken = "";
    String orderid = "";
    String industryType = "";
    String price = "";;
     String finalMediaPath = "";
    private ImageCompressTask imageCompressTask;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    TextView tv_uploaded1;
    ImageView iv_delete1;
    ImageView iv_secondDoc;
    int imageViewSeletor = 0;
    TextView tv_uploaded2;
    ImageView iv_delete2;
    TextView tv_hint1,tv_hint2;
    String docId = "";
    LinearLayout linear_front,linear_back;
    TextView tv_docHead;

    RecyclerView rv_uploadedDoc;
    ProgressBar rv_progress;
    String serviceid = "";
    List<UploadedData> uploadedDataList=null;
    AdapterUploadedDocument adapterUploadedDocument;
    String id = "";
    TextView tv_hintDoc;
    ImageView back_document;
    String gov_fee ="";
    String professional_fee = "";
    String company = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);

        commonSharedPreference = new CommonSharedPreference(this);

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                myToken = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
            } else {
                myToken = "";
            }
        }catch (NullPointerException ignored){

        }
        try {
             Intent intent = getIntent();
            orderid = intent.getStringExtra("orderId");
            id = intent.getStringExtra("id");
            industryType = intent.getStringExtra("industryType");
            price = intent.getStringExtra("price");
            gov_fee = intent.getStringExtra("gov_fee");
            professional_fee = intent.getStringExtra("professional_fee");
            company=intent.getStringExtra("company");
            serviceid =intent.getStringExtra("serviceid");




        }catch (NullPointerException ignored){

        }
        uploadedDataList=new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        spinner = findViewById(R.id.spinner);
        iv_pan = (ImageView)findViewById(R.id.iv_pan);
        iv_secondDoc = (ImageView)findViewById(R.id.iv_secondDoc);
        tv_uploaded1 = (TextView)findViewById(R.id.tv_uploaded1);
        iv_delete1 = (ImageView)findViewById(R.id.iv_delete1);
        tv_uploaded2 = (TextView)findViewById(R.id.tv_uploaded2);
        iv_delete2 = (ImageView)findViewById(R.id.iv_delete2);
        tv_hint1 = (TextView)findViewById(R.id.tv_hint1);
        tv_hint2 = (TextView)findViewById(R.id.tv_hint2);
        linear_front=(LinearLayout)findViewById(R.id.linrear_front);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);
        tv_docHead = (TextView)findViewById(R.id.tv_docHead);
        rv_uploadedDoc  =(RecyclerView)findViewById(R.id.rv_uploadedDoc);
        rv_progress = (ProgressBar)findViewById(R.id.rv_progress);
        tv_hintDoc = (TextView)findViewById(R.id.tv_hintDoc);
        back_document=(ImageView)findViewById(R.id.back_document);
        back_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(UploadDocument.this,OpenActivityDetail.class);
//                intent.putExtra("orderId",orderid);
//                intent.putExtra("id",id);
//                intent.putExtra("industryType",industryType);
//                intent.putExtra("price",price);
//                intent.putExtra("gov_fee",gov_fee);
//                intent.putExtra("professional_fee",professional_fee);
//                intent.putExtra("company",company);
//                intent.putExtra("serviceid",serviceid);
//                startActivity(intent);
                finish();
            }
        });


        myDocumentList=new HashMap<String, String>();
        documentId  =new ArrayList<>();
        documentTypeId  =new ArrayList<>();
        documentName = new ArrayList<>();



        getSpinnerValue(myToken,id,serviceid);
        iv_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewSeletor=1;
                openFileSelectorDialog(imageViewSeletor);

            }
        });

        iv_secondDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewSeletor =2;
                openFileSelectorDialog(imageViewSeletor);
            }
        });


//        iv_delete2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                deleteDocument(myToken,docId);
//                iv_delete2.setVisibility(View.GONE);
//                iv_secondDoc.setImageDrawable(null);
//                iv_secondDoc.setEnabled(true);
//                tv_uploaded2.setVisibility(View.GONE);
//                tv_hint2.setVisibility(View.VISIBLE);
//            }
//        });
//        iv_delete1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                progressDialog.show();
////                deleteDocument(myToken,docId);
//                iv_delete1.setVisibility(View.GONE);
//                iv_pan.setImageDrawable(null);
//                iv_pan.setEnabled(true);
//                tv_uploaded1.setVisibility(View.GONE);
//                tv_hint1.setVisibility(View.VISIBLE);
//
//            }
//        });



        getUploadedDocument(myToken,id,serviceid);

    }

    private void getUploadedDocument(String myToken, String id, String serviceid) {
        String token=myToken;
        String orderId=id;
        String serviceId =serviceid;

            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<UploadedDocumentModel> call = api.getMyUploadedDocument(token,serviceId,orderId);
            call.enqueue(new Callback<UploadedDocumentModel>() {
                @Override
                public void onResponse(Call<UploadedDocumentModel> call, Response<UploadedDocumentModel> response) {
                    rv_progress.setVisibility(View.GONE);
                    try {
                        uploadedDataList=response.body().getData();
                    }catch (NullPointerException ignored){

                    }
                    if (uploadedDataList.size()==0){
                        rv_uploadedDoc.setVisibility(View.GONE);
                    }else {


                        adapterUploadedDocument = new AdapterUploadedDocument(getApplicationContext(), uploadedDataList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        rv_uploadedDoc.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                        rv_uploadedDoc.setAdapter(adapterUploadedDocument);

                    }

                }

                @Override
                public void onFailure(Call<UploadedDocumentModel> call, Throwable t) {
                    rv_progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });

        }



        private void deleteDocument(String myToken, String docId) {
        String id = docId;
        String token = myToken;

            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<DeleteDocModel> call = api.deleteDoc(token,id);
            call.enqueue(new Callback<DeleteDocModel>() {
                @Override
                public void onResponse(Call<DeleteDocModel> call, Response<DeleteDocModel> response) {

                    if (response.equals("success")) {
                        iv_pan.setImageDrawable(null);
                        iv_pan.setEnabled(true);
                        tv_hint1.setVisibility(View.VISIBLE);
                        tv_uploaded1.setVisibility(View.GONE);
                        iv_delete1.setVisibility(View.GONE);
                        progressDialog.dismiss();

//                        if (imageViewSeletor == 1) {
//                            iv_delete1.setVisibility(View.VISIBLE);
//                            iv_pan.setEnabled(true);
//                            tv_hint1.setVisibility(View.VISIBLE);
//                            tv_uploaded1.setVisibility(View.VISIBLE);
//                            Toast.makeText(getApplicationContext(),"This documnet is deleted " +"1",Toast.LENGTH_SHORT).show();
//                        } else {
//                            iv_delete2.setVisibility(View.VISIBLE);
//                            iv_secondDoc.setEnabled(true);
//                            tv_hint2.setVisibility(View.VISIBLE);
//                            tv_uploaded2.setVisibility(View.VISIBLE);
//                            Toast.makeText(getApplicationContext(),"This documnet is deleted " +"2",Toast.LENGTH_SHORT).show();
//
//                        }
                    }

                }

                @Override
                public void onFailure(Call<DeleteDocModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                }
            });

        }



        private void openFileSelectorDialog(final int imageViewSeletor) {
        dialog = new Dialog(UploadDocument.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_option);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        TextView tv = (TextView) dialog.findViewById(R.id.tv);
        String invitation_text = ("Please select an option to send invitation to :- \n");

        LinearLayout linear_camera = (LinearLayout) dialog.findViewById(R.id.linear_camera);
        LinearLayout linear_gallery = (LinearLayout) dialog.findViewById(R.id.linear_gallery);


        linear_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(UploadDocument.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadDocument.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocument.this);
                            builder.setMessage("Need Media Access Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(UploadDocument.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
                                }
                            });
                            builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else {
                            //just request the permission
                            ActivityCompat.requestPermissions(UploadDocument.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
                        }
                    } else {
                        launchGalleryIntent(imageViewSeletor);
                        dialog.dismiss();
                    }
                } else {
                    launchGalleryIntent(imageViewSeletor);
                    dialog.dismiss();
                }
            }
        });


        linear_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(UploadDocument.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadDocument.this, Manifest.permission.CAMERA)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocument.this);
                            builder.setMessage("Need Camera Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(UploadDocument.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                                }
                            });
                            builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else {
                            //just request the permission
                            ActivityCompat.requestPermissions(UploadDocument.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                        }
                    } else {
                        //You already have the permission, just go ahead.
                        launchCameraIntent(imageViewSeletor);
                        dialog.dismiss();
                    }
                } else {
                    launchCameraIntent(imageViewSeletor);
                    dialog.dismiss();

                }
            }
        });
    }




    private void getSpinnerValue(String myToken,String id, String serviceid) {
        String token = myToken;
        String serviceId = serviceid;
        String orderId = id;

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<UploadDocumentSpinnerModel> call = api.getSpinnerValue(token, serviceId,orderId);
        call.enqueue(new Callback<UploadDocumentSpinnerModel>() {
            @Override
            public void onResponse(Call<UploadDocumentSpinnerModel> call, Response<UploadDocumentSpinnerModel> response) {

                try {

                    spinnerData = response.body().getData();
                    for (int i = 0; i < spinnerData.size(); i++) {
                        arrayList.add(spinnerData.get(i).getRequireDoc());

                        documentTypeId.add(spinnerData.get(i).getNooffile());
                        Collections.reverse(documentTypeId);
                        myDocumentList.put(String.valueOf(spinnerData.get(i).getId()), spinnerData.get(i).getRequireDoc());

                        Log.d("gchgcz", String.valueOf(myDocumentList));
                        Log.d("SDfa", String.valueOf(documentTypeId));
                    }
                    if (spinnerData.size()==0){
                        tv_hintDoc.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                    }else {
                        tv_hintDoc.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                    }




                    //Creating an ArrayList of keys
                    Set<String> keySet = myDocumentList.keySet();
                    ArrayList<String> listOfKeys = new ArrayList<String>(keySet);

                    for (String key : listOfKeys)
                    {
                        documentId.add(key);
                    }
                    Log.d("afass", String.valueOf(documentId));



                    //Creating an ArrayList of values
                    Collection<String> values = myDocumentList.values();

                    ArrayList<String> listOfValues = new ArrayList<String>(values);

                    for (String value : listOfValues)
                    {

                        documentName.add(value);
                    }
                    Log.d("afass", String.valueOf(documentName));




                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UploadDocument.this, android.R.layout.simple_spinner_item, documentName);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDocumentId =documentId.get(position);
                            documentSelectedValue =documentTypeId.get(position);

                            tv_docHead.setText(parent.getItemAtPosition(position).toString());
                            if (documentSelectedValue.equals("1")){
                                linear_front.setVisibility(View.VISIBLE);
                                linear_back.setVisibility(View.GONE);
                                iv_pan.setImageDrawable(null);
                                iv_secondDoc.setImageDrawable(null);
                                iv_pan.setEnabled(true);
                                iv_secondDoc.setEnabled(true);
                                tv_hint1.setVisibility(View.VISIBLE);
                                tv_hint2.setVisibility(View.VISIBLE);
                                iv_delete1.setVisibility(View.GONE);
                                iv_delete2.setVisibility(View.GONE);
                                tv_uploaded1.setVisibility(View.GONE);
                                tv_uploaded2.setVisibility(View.GONE);



                            }else {
                                linear_front.setVisibility(View.VISIBLE);
                                linear_back.setVisibility(View.VISIBLE);
                                iv_pan.setImageDrawable(null);
                                iv_secondDoc.setImageDrawable(null);
                                iv_pan.setEnabled(true);
                                iv_secondDoc.setEnabled(true);
                                tv_hint1.setVisibility(View.VISIBLE);
                                tv_hint2.setVisibility(View.VISIBLE);
                                iv_delete1.setVisibility(View.GONE);
                                iv_delete2.setVisibility(View.GONE);
                                tv_uploaded1.setVisibility(View.GONE);
                                tv_uploaded2.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }catch (NullPointerException ignored){

                }
            }

            @Override
            public void onFailure(Call<UploadDocumentSpinnerModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }






    private void launchGalleryIntent(int imageViewSeletor) {
        Intent intent = new Intent(UploadDocument.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    private void launchCameraIntent(int imageViewSeletor) {
        Intent intent = new Intent(UploadDocument.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    if (imageViewSeletor == 1) {
                        iv_pan.setImageURI(uri);

                    } else {
                        iv_secondDoc.setImageURI(uri);

                    }
                    saveBitmap(bitmap);


                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            String completePath = Environment.getExternalStorageDirectory() + "/" + "legaleaseDocument.jpg";

                            imageCompressTask = new ImageCompressTask(getApplicationContext(), completePath, iImageCompressTaskListener);

                            mExecutorService.execute(imageCompressTask);
                        }
                    }, 1500);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }






    public void saveBitmap(Bitmap bitmap) {

        File imagePath = new File("/sdcard/legaleaseDocument.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            boolean_save = true;


            Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }





    private IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
        @Override
        public void onComplete(List<File> compressed) {
            //photo compressed. Yay!

            //prepare for uploads. Use an Http library like Retrofit, Volley or async-http-client (My favourite)

            finalMediaPath = String.valueOf(compressed.get(0));
            Log.d("ghfhf", String.valueOf(finalMediaPath));

            Log.d("ImageCompressor", "New photo size ==> " + finalMediaPath.length()); //log new file size.

            progressDialog.show();

            File file1 = new File(finalMediaPath);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            MultipartBody.Part file = MultipartBody.Part.createFormData("file", file1.getName(), requestBody);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), myToken);
            RequestBody orderId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
            RequestBody serviceId = RequestBody.create(MediaType.parse("multipart/form-data"),serviceid );
            RequestBody uploadedType = RequestBody.create(MediaType.parse("multipart/form-data"), selectedDocumentId);



            callApi.uploadDoc(UploadDocument.this, file, token,orderId,serviceId,uploadedType);        }

        @Override
        public void onError(Throwable error) {
            //very unlikely, but it might happen on a device with extremely low storage.
            //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
            Log.wtf("ImageCompressor", "Error occurred", error);
        }
    };


    public void Response_uploadDoc(UploadDocModel body) {
        progressDialog.dismiss();
        String response = "";
        String imageUrl = "";
        try {
            response = body.getStatus();
            imageUrl = IMAGEURL+body.getData().getUploadedDoc();
            docId = String.valueOf(body.getData().getId());
        }catch (NullPointerException ignored){

        }
        if (response.equals("success")){
            if (imageViewSeletor==1){
                tv_hint1.setVisibility(View.GONE);
                tv_uploaded1.setVisibility(View.VISIBLE);
                iv_delete1.setVisibility(View.GONE);
                iv_pan.setEnabled(false);



            }else {
                tv_hint1.setVisibility(View.GONE);
                tv_uploaded2.setVisibility(View.VISIBLE);
                iv_delete2.setVisibility(View.GONE);
                iv_secondDoc.setEnabled(false);
                iv_delete2.setEnabled(false);


            }

        }else {
            tv_uploaded1.setVisibility(View.GONE);
            iv_delete1.setVisibility(View.GONE);
            iv_pan.setEnabled(true);
            tv_uploaded2.setVisibility(View.GONE);
            iv_delete2.setVisibility(View.GONE);
            iv_secondDoc.setEnabled(false);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(UploadDocument.this,OpenActivityDetail.class);
//        intent.putExtra("orderId",orderid);
//        intent.putExtra("id",id);
//        intent.putExtra("industryType",industryType);
//        intent.putExtra("price",price);
//        intent.putExtra("gov_fee",gov_fee);
//        intent.putExtra("professional_fee",professional_fee);
//        intent.putExtra("company",company);
//        intent.putExtra("serviceid",serviceid);
//        startActivity(intent);
        finish();
    }
}


