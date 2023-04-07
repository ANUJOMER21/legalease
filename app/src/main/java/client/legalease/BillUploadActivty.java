package client.legalease;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.legalease.Adapter.AdapterDocument;
import client.legalease.Adapter.Common.ImageAdapter;
import client.legalease.Common.CameraUtils;
import client.legalease.Common.FilePath;
import client.legalease.Common.FileUtils;
import client.legalease.Common.ImagePickerActivity;
import client.legalease.Common.ItemOffsetDecoration;
import client.legalease.Interface.IImageCompressTaskListener;
import client.legalease.Model.MyDocuments.DocumentData;
import client.legalease.Model.MyDocuments.MyDocumentModel;
import client.legalease.Model.UploadBillModel;
import client.legalease.Model.UploadDocModel.UploadDocModel;
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

public class BillUploadActivty extends AppCompatActivity {

    String parentId = "";
    String title = "";
    TextView serviceTitle;
    ImageView back;
    FloatingActionButton fab;
    Dialog dialog, dialogMonth, dialogCategory;

    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int STORAGE_PERMISSION_CONSTANT = 101;
    public static final int REQUEST_IMAGE = 100;
    public static final int CAMERA_PIC_REQUEST = 101;

    ImageView iv_image;
    boolean boolean_save;
    ProgressDialog progressDialog;

    private ImageCompressTask imageCompressTask;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    String finalMediaPath = "";
    CallApi callApi = new CallApi();
    String myToken = "";
    String type = "1";
    String month = "";
    String year = "";
    CommonSharedPreference commonSharedPreference;
    TextView tv_sale, tv_expanses, tv_others;
    int selectDialog = 1;

    //    ArrayList<String> monthList = new ArrayList<>();
    String[] monthList = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] yearList = {"2016", "2017", "2018", "2019",
            "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

    TextView tv_selectMonth;
    TextView tv_year;
    String categorySelector = "";


    String imageEncoded;
    List<String> imagesEncodedList;
    RecyclerView rv_document;
    List<DocumentData> documentDataList = new ArrayList<>();
    TextView button_search;
    AdapterDocument adapterDocument;
    ProgressBar progressBar;
    TextView tv_noData;
    RelativeLayout relative_hint;
    String id = "";
    ProgressDialog pDialog;
    private ArrayList<Uri> fileUris = new ArrayList();
    RecyclerView rv_images;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final String IMAGE_EXTENSION = "jpg";
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static String imageStoragePath;
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_PDF = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int BITMAP_SAMPLE_SIZE = 8;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    Uri path;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_upload_activty);


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

        initializeViews();


        Calendar calendar = Calendar.getInstance();
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        year = String.valueOf(calendar.get(Calendar.YEAR));




        int m= Integer.parseInt(month);
        String monthString="";
        //Switch statement
        switch(m) {
            //case statements within the switch block
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;


        }
        tv_selectMonth.setText(monthString);
        tv_year.setText(year);


        try {
            Intent intent = getIntent();
            parentId = intent.getStringExtra("parentId");
            title = intent.getStringExtra("serviceTitle");
            serviceTitle.setText(title);
        } catch (NullPointerException ignored) {

        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_selectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMonthListDialog();
            }
        });


        tv_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYearListDialog();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCategoryDialog();

            }
        });



        tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";
//                tv_sale.setBackgroundColor(R.drawable.option_background_afterselection);
//                tv_expanses.setBackgroundColor(R.drawable.option_background);
//                tv_others.setBackgroundColor(R.drawable.option_background);
                tv_sale.setTextColor(getResources().getColor(R.color.white));
                tv_expanses.setTextColor(getResources().getColor(R.color.black_80));
                tv_others.setTextColor(getResources().getColor(R.color.black_80));


            }
        });
        tv_expanses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "2";
//                tv_sale.setBackgroundColor(R.drawable.option_background);
//                tv_expanses.setBackgroundColor(R.drawable.option_background_afterselection);
//                tv_others.setBackgroundColor(R.drawable.option_background);
                tv_sale.setTextColor(getResources().getColor(R.color.black_80));
                tv_expanses.setTextColor(getResources().getColor(R.color.white));
                tv_others.setTextColor(getResources().getColor(R.color.black_80));
            }
        });
        tv_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "3";

//                tv_sale.setBackgroundColor(R.drawable.option_background);
//                tv_expanses.setBackgroundColor(R.drawable.option_background);
//                tv_others.setBackgroundColor(R.drawable.option_background_afterselection);
                tv_sale.setTextColor(getResources().getColor(R.color.black_80));
                tv_expanses.setTextColor(getResources().getColor(R.color.black_80));
                tv_others.setTextColor(getResources().getColor(R.color.white));
            }
        });





        getMyDocument(myToken, type, month, year);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getMyDocument(myToken, type, month, year);
            }
        });





    }





    private void getMyDocument(String myToken, String type, String month, String year) {

        String token = myToken;
        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<MyDocumentModel> call = api.getDocument(token,type,month,year);
        call.enqueue(new Callback<MyDocumentModel>() {
            @Override
            public void onResponse(Call<MyDocumentModel> call, Response<MyDocumentModel> response) {

                progressBar.setVisibility(View.GONE);
                try {
                    documentDataList = response.body().getData();
                    if (documentDataList.size()==0){
                        relative_hint.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.VISIBLE);
                    }else {

                        relative_hint.setVisibility(View.VISIBLE);
                        tv_noData.setVisibility(View.GONE);
                        adapterDocument = new AdapterDocument(BillUploadActivty.this,documentDataList);

                        LinearLayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                        rv_document.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                        rv_document.setAdapter(adapterDocument);

                    }
                }catch (NullPointerException ignored){

                }


            }

            @Override
            public void onFailure(Call<MyDocumentModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }







    private void openMonthListDialog() {
        dialogMonth = new Dialog(BillUploadActivty.this);
        dialogMonth.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogMonth.setContentView(R.layout.dialog_month);
        dialogMonth.setCanceledOnTouchOutside(true);
        dialogMonth.setCancelable(true);
        dialogMonth.show();


        ListView listView = (ListView) dialogMonth.findViewById(R.id.lv_month);
        TextView textView = (TextView) findViewById(R.id.tv_month);
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, monthList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                int value = position + 1;
                month = String.valueOf(value);
                tv_selectMonth.setText(adapter.getItem(position));
                dialogMonth.dismiss();

            }
        });

    }

    private void openYearListDialog() {
        dialogMonth = new Dialog(BillUploadActivty.this);
        dialogMonth.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogMonth.setContentView(R.layout.dialog_month);
        dialogMonth.setCanceledOnTouchOutside(true);
        dialogMonth.setCancelable(true);
        dialogMonth.show();


        ListView listView = (ListView) dialogMonth.findViewById(R.id.lv_month);
        TextView textView = (TextView) findViewById(R.id.tv_month);
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, yearList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                year = adapter.getItem(position);
                tv_year.setText(year);
                dialogMonth.dismiss();

            }
        });

    }


    private void chooseCategoryDialog() {


        dialogCategory = new Dialog(BillUploadActivty.this);
        dialogCategory.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogCategory.setContentView(R.layout.dialog_select_categoryoption);
        dialogCategory.setCanceledOnTouchOutside(true);
        dialogCategory.setCancelable(true);
        dialogCategory.show();
        TextView tv_saleSelector = (TextView) dialogCategory.findViewById(R.id.tv_saleSelector);
        TextView tv_expensesSelector = (TextView) dialogCategory.findViewById(R.id.tv_expensesSelector);
        TextView tv_otherSelector = (TextView) dialogCategory.findViewById(R.id.tv_otherSelector);

        String invitation_text = ("Please select an option to send invitation to :- \n");


        tv_saleSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector = "1";
                openFileSelectorDialog(categorySelector);
                dialogCategory.dismiss();

            }
        });
        tv_expensesSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector = "2";
                openFileSelectorDialog(categorySelector);
                dialogCategory.dismiss();
            }
        });
        tv_otherSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySelector = "3";
                openFileSelectorDialog(categorySelector);
                dialogCategory.dismiss();
            }
        });

    }

    private void openFileSelectorDialog(String categorySelector) {
        dialog = new Dialog(BillUploadActivty.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_option);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        TextView tv = (TextView) dialog.findViewById(R.id.tv);
        String invitation_text = ("Please select an option to send invitation to :- \n");

        LinearLayout linear_camera = (LinearLayout) dialog.findViewById(R.id.linear_camera);
        LinearLayout linear_gallery = (LinearLayout) dialog.findViewById(R.id.linear_gallery);
        LinearLayout linear_pdf = (LinearLayout) dialog.findViewById(R.id.linear_pdf);


        linear_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(BillUploadActivty.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(BillUploadActivty.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(BillUploadActivty.this);
                            builder.setMessage("Need Media Access Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(BillUploadActivty.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
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
                            ActivityCompat.requestPermissions(BillUploadActivty.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
                        }
                    } else {
                        fab.hide();
                        launchPdfIntent();
                        dialog.dismiss();
                    }
                } else {
                    launchPdfIntent();
                    dialog.dismiss();
                }


            }
        });



        linear_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(BillUploadActivty.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(BillUploadActivty.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(BillUploadActivty.this);
                            builder.setMessage("Need Media Access Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(BillUploadActivty.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
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
                            ActivityCompat.requestPermissions(BillUploadActivty.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
                        }
                    } else {
                        fab.hide();
                        launchGalleryIntent();
                        dialog.dismiss();
                    }
                } else {
                    launchGalleryIntent();
                    dialog.dismiss();
                }
            }
        });


        linear_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(BillUploadActivty.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(BillUploadActivty.this, Manifest.permission.CAMERA)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(BillUploadActivty.this);
                            builder.setMessage("Need Camera Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(BillUploadActivty.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                            ActivityCompat.requestPermissions(BillUploadActivty.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                        }
                    } else {
                        //You already have the permission, just go ahead.
                        launchCameraIntent();
                        dialog.dismiss();
                    }
                } else {
                    launchCameraIntent();
                    dialog.dismiss();

                }
            }
        });
    }


    private void initializeViews() {
        serviceTitle = (TextView) findViewById(R.id.serviceTitle);
        back = (ImageView) findViewById(R.id.back);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_sale = (TextView) findViewById(R.id.tv_sale);
        tv_expanses = (TextView) findViewById(R.id.tv_expanses);
        tv_others = (TextView) findViewById(R.id.tv_others);
        tv_selectMonth = (TextView) findViewById(R.id.tv_selectMonth);
        tv_year = (TextView) findViewById(R.id.tv_year);
        rv_document  = (RecyclerView)findViewById(R.id.rv_document);
        button_search = (TextView) findViewById(R.id.button_search);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tv_noData = (TextView)findViewById(R.id.tv_noData);
        relative_hint =(RelativeLayout)findViewById(R.id.relative_hint);
        rv_images = (RecyclerView)findViewById(R.id.rv_images);


    }



    private void launchPdfIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), REQUEST_CODE_PDF);


    }


    private void launchGalleryIntent() {

        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }


    private void launchCameraIntent() {


        Intent intent = new Intent(BillUploadActivty.this,MultipleCameraImageActivity.class);
        intent.putExtra("categorySelector",categorySelector);
        startActivity(intent);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            Log.e("sfa", "Uri Selected" + imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);

                                fileUris.add(imageUri);


                            } catch (Exception e) {
                            }
                        }
                        uploadFiles(fileUris);
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.e("Single File Selected", path);

                            fileUris.add(uri);
                            uploadFiles(fileUris);
//                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                            fileUris.add(bitmap);
//                            initRecyclerView();
                        } catch (Exception e) {
                        }
                    }
                }
                break;
            case REQUEST_CODE_PDF:
                    if (resultCode == RESULT_OK) {
                        if (data.getData() != null) {
                            final Uri uri = data.getData();
                          String  PdfPathHolder = FilePath.getPath(this, uri);

                            pDialog = new ProgressDialog(BillUploadActivty.this);


                            String pleaseWait = "";
                                pleaseWait = "Please wait... your Pdf is getting uploaded";

                            pDialog.setMessage(pleaseWait);
                            pDialog.setIndeterminate(false);
                            pDialog.setCancelable(false);
                            pDialog.show();

                            if (PdfPathHolder == null) {

                                Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

                            }else {
                                try {
                                    File file = new File(PdfPathHolder);

                                    // Parsing any Media type file
                                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                    MultipartBody.Part filex = MultipartBody.Part.createFormData("filex", file.getName(), requestBody);
                                    RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), categorySelector);

                                    RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), id);


                                    callApi.uploadBillDoc(BillUploadActivty.this,filex,userId,type);
                                }catch (NullPointerException ignored){

                                }catch (IndexOutOfBoundsException ignored){

                                }
                            }


                        }
                    }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }







    private void uploadFiles(List<Uri> fileUris) {
        pDialog = new ProgressDialog(BillUploadActivty.this);
        String a ="";
        try {
            a = String.valueOf(fileUris.size());
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignored){

        }

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
            for (int i=0;i<fileUris.size();i++){
                filex.add(prepareFilePart("filex[]", fileUris.get(i)));

            }
            RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), categorySelector);

            RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), id);


            callApi.uploadBill(BillUploadActivty.this,filex,userId,type);
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignored){

        }
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public void Response_uploadBill(UploadBillModel body) {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "File uploaded SuccesFully", Toast.LENGTH_SHORT).show();
    }

    public void checkMessage() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(), "Something went wrong... please try after sometimes", Toast.LENGTH_SHORT).show();
    }




    private void initRecyclerView() {
        int spacing = 7; // 50px
        rv_images.addItemDecoration(new ItemOffsetDecoration(spacing));

        rv_images.setLayoutManager(new GridLayoutManager(this, 2));
        ImageAdapter adapter = new ImageAdapter(fileUris);
        rv_images.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    public void Response_billUplaod(UploadBillModel body) {
        pDialog.dismiss();


        Toast.makeText(getApplicationContext(),"File uploaded Susccesfully",Toast.LENGTH_SHORT).show();
    }

    public void closeDialog() {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Oops something went wrong... please try after sometime",Toast.LENGTH_SHORT).show();
    }
}


