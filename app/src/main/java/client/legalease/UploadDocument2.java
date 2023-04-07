package client.legalease;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.legalease.Adapter.UploadedDocumentAdapter2;
import client.legalease.Common.ImagePickerActivity;
import client.legalease.Interface.IImageCompressTaskListener;
import client.legalease.Model.UploadDocServerModel.Uploaddocumentserver;
import client.legalease.Model.Uploadeddocmodel;
import client.legalease.Model.uploaddocumentlistModel.Datum;
import client.legalease.Model.uploaddocumentlistModel.Uploaddocumentlist;
import client.legalease.Model.uploadedDocuments.Datum3;
import client.legalease.Model.uploadedDocuments.Uploaddocuments;
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
public class UploadDocument2 extends AppCompatActivity {
Spinner spinner;
String mytoken="";
    String path;
    Dialog dialog;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    private ImageCompressTask imageCompressTask;
    int imageViewSeletor = 0;
    String reqDocID;


    public static final int REQUEST_IMAGE = 100;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int STORAGE_PERMISSION_CONSTANT = 101;
    ImageView iv_pan;
    TextView tv_uploaded2;
    TextView tv_hint1,tv_hint2;
    TextView tv_uploaded1;
    ImageView iv_delete1;
    ImageView back;
    ImageView iv_delete2;
    ImageView iv_secondDoc;
   List<Datum> datumArrayList=null;
HashMap<Integer,String> documentidname;
    private String orderid;
    LinearLayout linear_front,linear_back;
    private String id;
    private String industryType;
    private String price;
    private String gov_fee;
    private String professional_fee;
    private String company;
    private String serviceid;
private RecyclerView rv_uploadedDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document2);
        initview();

        CommonSharedPreference commonSharedPreference = new CommonSharedPreference(this);

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                mytoken = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
            } else {
                mytoken = "";
            }
        }catch (NullPointerException ignored){

        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        datumArrayList=new ArrayList<>();
        documentidname =new HashMap<>();
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderId");
        id = intent.getStringExtra("id");
        industryType = intent.getStringExtra("industryType");
        price = intent.getStringExtra("price");
        gov_fee = intent.getStringExtra("gov_fee");
        professional_fee = intent.getStringExtra("professional_fee");
        company=intent.getStringExtra("company");
        serviceid =intent.getStringExtra("serviceid");
        initspinner(mytoken);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getuploadeddoc(HashMap<Integer, String> documentidname2) {
        if(documentidname2.size()!=0){
            String token="Bearer "+ mytoken;
            ApiService apiService=UserMoreDetailActivity.RetroClient.getApiService();
            Call<Uploaddocuments> uploaddocumentsCall= apiService.geuptdoc(token,orderid);

            uploaddocumentsCall.enqueue(new Callback<Uploaddocuments>() {
                @Override
                public void onResponse(Call<Uploaddocuments> call, Response<Uploaddocuments> response) {
                    Log.d("uploadres", "onResponse: "+response);
                    if(response.body().getStatus().equals("success")){
                        progressDialog.dismiss();
                        ArrayList<Uploadeddocmodel> uploadeddocmodelArrayList=new ArrayList<>();
                        List<Datum3> datum3s=new ArrayList<>();
                        datum3s=response.body().getData();
                        for (Datum3 data:datum3s
                             ) {
                       String nameofdoc="";
                       if(documentidname2.containsKey(data.getRequireDocId())){
                           nameofdoc=documentidname2.get(data.getRequireDocId());
                           uploadeddocmodelArrayList.add(new Uploadeddocmodel(nameofdoc,data.getUploadedDoc()));
                           Log.d("namedoc", "onResponse: "+nameofdoc);
                       }
                            Log.d(data.getId().toString(), "onResponse: "+data.getUploadedDoc());

                        }
                       if(uploadeddocmodelArrayList.size()!=0) {
                           rv_uploadedDoc.setVisibility(View.VISIBLE);
                           RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                           rv_uploadedDoc.setLayoutManager(eLayoutManager);
                           UploadedDocumentAdapter2 uploadedDocumentAdapter2=new UploadedDocumentAdapter2(uploadeddocmodelArrayList,UploadDocument2.this);
                           rv_uploadedDoc.setAdapter(uploadedDocumentAdapter2);

                       }

                    }
                }

                @Override
                public void onFailure(Call<Uploaddocuments> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });


        }
        else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }




    }

    private void initspinner(String mytoken) {

        String token="Bearer "+ mytoken;
        ApiService apiService=UserMoreDetailActivity.RetroClient.getApiService();
        Call<Uploaddocumentlist> uploaddocumentlistCall=apiService.uploaddocumentlist(token);
        uploaddocumentlistCall.enqueue(new Callback<Uploaddocumentlist>() {
            @Override
            public void onResponse(Call<Uploaddocumentlist> call, Response<Uploaddocumentlist> response) {
              datumArrayList=  response.body().getData();
           ArrayList<String> documentlist=new ArrayList<>();

           for(Datum datum :datumArrayList){
               documentlist.add(datum.getRequireDoc());
                  documentidname.put(datum.getId(),datum.getRequireDoc());
           }
                getuploadeddoc(documentidname);
                Log.d("documentidname", "onResponse: "+documentidname.size());
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(UploadDocument2.this,android.R.layout.simple_spinner_item,documentlist);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                Log.d("ud2", "onResponse: "+response.toString()+mytoken);
             spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                      reqDocID=String.valueOf(datumArrayList.get(position).getId());

                     if (datumArrayList.get(position).getNooffile() == 1) {
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


                     } else {
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

           //uploaddocument(path);


            }

            @Override
            public void onFailure(Call<Uploaddocumentlist> call, Throwable t) {

            }
        });


    }

    private void uploaddocument(String path) {
        progressDialog.show();
        File file1 = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file", file1.getName(), requestBody);
        //   RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), mytoken);
        String token="Bearer "+mytoken;
        RequestBody orderId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody requireddocid = RequestBody.create(MediaType.parse("multipart/form-data"),reqDocID );
        RequestBody uploadedType = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(imageViewSeletor));
        callApi.uploaddoc2(UploadDocument2.this, file, token,orderId,uploadedType,requireddocid);


    }

    private void initview() {
    spinner=findViewById(R.id.spinner);
        linear_front=(LinearLayout)findViewById(R.id.linrear_front);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);
        iv_pan = (ImageView)findViewById(R.id.iv_pan);
        iv_secondDoc = (ImageView)findViewById(R.id.iv_secondDoc);
        tv_uploaded1 = (TextView)findViewById(R.id.tv_uploaded1);
        iv_delete1 = (ImageView)findViewById(R.id.iv_delete1);
        tv_uploaded2 = (TextView)findViewById(R.id.tv_uploaded2);
        iv_delete2 = (ImageView)findViewById(R.id.iv_delete2);
        back=(ImageView)findViewById(R.id.back_document);
        tv_hint1 = (TextView)findViewById(R.id.tv_hint1);
        tv_hint2 = (TextView)findViewById(R.id.tv_hint2);
        linear_front=(LinearLayout)findViewById(R.id.linrear_front);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);
        rv_uploadedDoc=(RecyclerView) findViewById(R.id.rv_uploadedDoc);

    }
    private void openFileSelectorDialog(final int imageViewSeletor) {
        dialog = new Dialog(UploadDocument2.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dialog_option);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        TextView tv = (TextView) dialog.findViewById(R.id.tv);
        String invitation_text = ("Please select an option to send invitation to :- \n");

        LinearLayout linear_camera = (LinearLayout) dialog.findViewById(R.id.linear_camera);
        LinearLayout linear_gallery = (LinearLayout) dialog.findViewById(R.id.linear_gallery);
LinearLayout linear_pdf=(LinearLayout)dialog.findViewById(R.id.linear_pdf);
linear_pdf.setVisibility(View.GONE);

        linear_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /**  if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(UploadDocument2.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadDocument2.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocument2.this);
                            builder.setMessage("Need Media Access Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(UploadDocument2.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
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
                            ActivityCompat.requestPermissions(UploadDocument2.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CONSTANT);
                        }
                    } else {
                        launchGalleryIntent(imageViewSeletor);
                        dialog.dismiss();
                    }
                }
                else {
                    launchGalleryIntent(imageViewSeletor);
                    dialog.dismiss();
                }**/
              if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                  Intent intent=new Intent();
                  intent.setType("image/*");
                  intent.setAction(Intent.ACTION_GET_CONTENT);
                  startActivityForResult(intent,10);
              }
              else {
                  ActivityCompat.requestPermissions(UploadDocument2.this,
                          new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
              }
            }
        });


        linear_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(UploadDocument2.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadDocument2.this, android.Manifest.permission.CAMERA)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocument2.this);
                            builder.setMessage("Need Camera Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(UploadDocument2.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                            ActivityCompat.requestPermissions(UploadDocument2.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
    private void launchCameraIntent(int imageViewSeletor) {
        Intent intent = new Intent(UploadDocument2.this, ImagePickerActivity.class);
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
    private void launchGalleryIntent(int imageViewSeletor) {
        Intent intent = new Intent(UploadDocument2.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
          if (requestCode == REQUEST_IMAGE) {
         if (resultCode == Activity.RESULT_OK) {
         Uri uri = data.getParcelableExtra("path");
             //Log.d("uriud2", "onActivityResult: "+uri);
         try {
         // You can update this bitmap to your server
         Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
         if (imageViewSeletor == 1) {
         iv_pan.setImageURI(uri);

         } else {
         iv_secondDoc.setImageURI(uri);

         }
             String path=getRealPathFromURI(uri);

             Log.d("uriud2", "onActivityResult: "+path);
       //  saveBitmap(bitmap);
             uploaddocument(path);


       /**  new Handler().postDelayed(new Runnable() {

        @Override public void run() {

        String completePath = Environment.getExternalStorageDirectory() + "/" + "legaleaseDocument.jpg";

        imageCompressTask = new ImageCompressTask(getApplicationContext(), completePath, iImageCompressTaskListener);

        mExecutorService.execute(imageCompressTask);
        }
        }, 1500);**/


         } catch (IOException e) {
         e.printStackTrace();
         }
         }
         }
       else if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = UploadDocument2.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
          if(imageViewSeletor==1){
            iv_pan.setImageBitmap(bitmap);}
          else{
              iv_secondDoc.setImageBitmap(bitmap);
          }
          uploaddocument(path);
          dialog.dismiss();

        }
       /** else if(requestCode== REQUEST_IMAGE&&requestCode==Activity.RESULT_OK){
            Uri uri = data.getParcelableExtra("path");
            Log.d("uriud2", "onActivityResult: "+uri);
            try {
                // You can update this bitmap to your server
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                if (imageViewSeletor == 1) {
                    iv_pan.setImageBitmap(bitmap);

                } else {
                    iv_secondDoc.setImageBitmap(bitmap);

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }**/
    }

    private String getRealPathFromURI(Uri contentURI) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }


    boolean boolean_save;

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




    String finalMediaPath = "";
    ProgressDialog progressDialog;
    CallApi callApi  =new CallApi();
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
         //   RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), mytoken);
           String token="Bearer "+mytoken;
            RequestBody orderId = RequestBody.create(MediaType.parse("multipart/form-data"), id);
            RequestBody requireddocid = RequestBody.create(MediaType.parse("multipart/form-data"),reqDocID );
            RequestBody uploadedType = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(imageViewSeletor));



            callApi.uploaddoc2(UploadDocument2.this, file, token,orderId,uploadedType,requireddocid);        }

        @Override
        public void onError(Throwable error) {
            //very unlikely, but it might happen on a device with extremely low storage.
            //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
            Log.wtf("ImageCompressor", "Error occurred", error);
        }
    };
    String docId = "";

    public void Response_uploadDoc(Uploaddocumentserver body) {
       if(body!=null) {


           progressDialog.dismiss();

           String response = "";
           String imageUrl = "";
           try {
               response = body.getStatus();

           } catch (NullPointerException ignored) {

           }
           if (response.equals("success")) {
               if (imageViewSeletor == 1) {
                   tv_hint1.setVisibility(View.GONE);
                   tv_uploaded1.setVisibility(View.VISIBLE);
                   iv_delete1.setVisibility(View.GONE);
                   iv_pan.setEnabled(false);


               } else {
                   tv_hint1.setVisibility(View.GONE);
                   tv_uploaded2.setVisibility(View.VISIBLE);
                   iv_delete2.setVisibility(View.GONE);
                   iv_secondDoc.setEnabled(false);
                   iv_delete2.setEnabled(false);


               }

           } else {
               tv_uploaded1.setVisibility(View.GONE);
               iv_delete1.setVisibility(View.GONE);
               iv_pan.setEnabled(true);
               tv_uploaded2.setVisibility(View.GONE);
               iv_delete2.setVisibility(View.GONE);
               iv_secondDoc.setEnabled(false);


           }
       }
       else {
           progressDialog.dismiss();
       }
    }
}