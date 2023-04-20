package client.legalease;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.Common.ImagePickerActivity;
import client.legalease.Interface.IImageCompressTaskListener;
import client.legalease.Model.ClientOrderModel;
import client.legalease.Model.CustomerInfo.CustomerInfo;
import client.legalease.Model.LoginModel.User;
import client.legalease.Model.State;
import client.legalease.Model.StateListModel.StateData;
import client.legalease.Model.StateListModel.StateModel;
import client.legalease.Model.UpdateUserProfile.UserUpdate;
import client.legalease.Model.Uploadimageprofile;
import client.legalease.Model.Userupdate;
import client.legalease.Model.updateprofilemodel.Updateprofilemodel;
import client.legalease.Model.updateprofilemodel.Userdetails;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import client.legalease.Utilities.ImageCompressTask;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView back;
    CommonSharedPreference commonSharedPreference;

    int id = 0;
    String myToken ="";
    String name ="";
    String email = "";
    String gst;
    String id_cms_privileges = "";
    String mobile ="";
    String country = "";
    String state = "";
    String city = "";
    String dob="";
    String pincode = "";
    String address = "";
    String qualification = "";
    String profileImage = "";
    EditText et_name,et_mobile,et_dob,et_pincode,et_city,et_gst;
Spinner etstate;
    LinearLayout linear_addImageFromGallery,linear_addImageFromCamera;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int STORAGE_PERMISSION_CONSTANT = 101;
    public static final int REQUEST_IMAGE_GALLERY = 102;
    public static final int REQUEST_IMAGE_CAMERA = 103;

    de.hdodenhof.circleimageview.CircleImageView img_addImage;
    boolean boolean_save;
    ExpandableRelativeLayout expandableLayout1;
    ImageView iv_check;
    String profile_name ="";
    String profile_dob="";
    String profile_email = "";
    String profile_id_cms_privileges = "";
    String profile_mobile ="";
    String profile_country = "";
    String profile_gst="";
    String profile_state = "";

    String profile_city = "";
    String profile_pincode = "";
    String profile_address = "";
    String profile_qualification = "";
    private ImageCompressTask imageCompressTask;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    String finalMediaPath;
    ProgressDialog progressDialog;
    CallApi callApi = new CallApi();

    User userdata;
String  path;

    List<StateData> stateData =null;
    HashMap<String,String> myStateList =null;
    ArrayList<String> finalStateList =null;
    ArrayList<String> finalStateId =null;

    String selectedStateId;
    String finalImageValue = "";
    public static final int REQUEST_IMAGE = 100;



List<StateData> statelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        statelist=new ArrayList<>();
        initializeView();
        setStatearray();
      //  Log.d("state", "onCreate: "+statelist.size());
        commonSharedPreference = new CommonSharedPreference(this);
        myToken=commonSharedPreference.getToken();
        try {
          /**  if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                Log.d("updateprofile1", "onCreate: run");
                Log.d("updateprofile start", "onCreate: "+commonSharedPreference.getLoginSharedPref(getApplicationContext()));
                myToken = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();
                name = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getName();
                email = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getEmail();
             //   profileImage = (String) commonSharedPreference.getLoginSharedPref(getApplicationContext()).getPhoto();
                id_cms_privileges = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getIdCmsPrivileges();
                mobile = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getMobile();
//                country = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getCountry();
              state = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getState();
               gst=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getGstNo();
              city = (String) commonSharedPreference.getLoginSharedPref(getApplicationContext()).getCity();
             // pincode = (String) commonSharedPreference.getLoginSharedPref(getApplicationContext()).getPincode();
//                address = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getAddress();
//                qualification = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getQualification();
               finalImageValue =  commonSharedPreference.getProfilephoto(UpdateActivity.this);
                Log.d("finalimagevalue", "onCreate: "+finalImageValue);
                dob=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getDob();

                et_name.setText(name);
                et_dob.setText(dob);
                et_mobile.setText(mobile);
                et_pincode.setText(pincode);
                et_city.setText(city);
                etstate.setSelection(Integer.valueOf(state));
                et_gst.setText(gst);



            }
            else if (commonSharedPreference.getLoginsharedpref2(getApplicationContext())!=null){
                Log.d("updateprofile2", "onCreate: run");
                Gson gson=new Gson();
                client.legalease.Model.VERIFYOTP.User user =  commonSharedPreference.getLoginsharedpref2(getApplicationContext());
                finalImageValue = IMAGEURL+ user.getPhoto();
                et_name.setText(user.getName());
                et_dob.setText(user.getDob());
                et_mobile.setText(user.getMobile());
                et_pincode.setText( String.valueOf(user.getPincode()));
                et_city.setText((CharSequence) user.getCity());
etstate.setSelection(Integer.parseInt(user.getState()));
                et_gst.setText(user.getGstNo());
                Log.d("photo Rec", "onCreate: "+user.getPhoto());




            }**/
          setdata();
        }catch (NullPointerException ignored){
            Log.d("updateprofile error", "onCreate: "+ignored);
        }catch (IndexOutOfBoundsException ignore){
            Log.d("updateprofile error", "onCreate: "+ignore);
        }
        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                et_dob.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });
        etstate.setOnItemSelectedListener(this);



              back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UpdateActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");



     linear_addImageFromGallery.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                 Intent intent = new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(intent, 10);

             }
             else{
                 ActivityCompat.requestPermissions(UpdateActivity.this,
                         new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
             }
         }

     });
     /**   linear_addImageFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(UpdateActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateActivity.this, Manifest.permission.CAMERA)) {
                            //Show Information about why you need the permission
                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                            builder.setMessage("Need Camera Permission \n");
                            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                            ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                        }
                    } else {
                        //You already have the permission, just go ahead.
                        launchCameraIntent();
                    }
                }else {
                    launchCameraIntent();

                }

            }
        });**/
linear_addImageFromCamera.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(UpdateActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateActivity.this, android.Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setMessage("Need Camera Permission \n");
                    builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
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
                    ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }
            } else {
                //You already have the permission, just go ahead.
                launchCameraIntent();

            }
        } else {
            launchCameraIntent();


        }

    }
});


        iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_name = et_name.getText().toString().trim();
                profile_mobile =et_mobile.getText().toString().trim();
                profile_dob=et_dob.getText().toString().trim();
                profile_pincode = et_pincode.getText().toString().trim();
                profile_city = et_city.getText().toString().trim();
       //         profile_state = etState.getText().toString().trim();
                profile_country = "India";
                profile_gst=et_gst.getText().toString().trim();

                       String profileStatename= (String) etstate.getSelectedItem();
                       int statepos=0;
                for (StateData s:statelist
                     ) {
                    if(profileStatename.equals(s.getStateName())){
                        statepos=s.getId();
                        break;
                    }

                }
                profile_state= String.valueOf(statepos);
                Log.d("profile_state", "onClick: "+profile_state);

                if (profile_name.equals("")) {
                    et_name.setError("Please Enter Name");
                    et_name.requestFocus();


                }
                else if (profile_dob.equals("")) {
                    et_dob.setError("Please Enter Date of birth");
                    et_dob.requestFocus();


                }
                else if (profile_mobile.length() < 10  || profile_mobile.equalsIgnoreCase("9876543210")
                        || profile_mobile.equalsIgnoreCase("0123456789")) {
                    et_mobile.setError("Please Enter Valid Mobile Number");
                    et_mobile.requestFocus();
                }
                else if (profile_pincode.equals("")) {
                    et_pincode.setError("Please Enter Pincode");
                    et_pincode.requestFocus();


                } else if (profile_city.equals("")) {
                    et_city.setError("Please Enter Pincode");
                    et_city.requestFocus();


                }else if (profile_state.equals("")) {
                   // etState.setError("Please Enter State");
                  //  etState.requestFocus();


                }
               else {
sendtodb(profile_name,profile_dob,profile_mobile,profile_pincode,profile_city,profile_state,profile_gst,myToken);


              /**      String completePath = Environment.getExternalStorageDirectory() + "/" + "legaleaseProfile.jpg";

                    imageCompressTask = new ImageCompressTask(getApplicationContext(), completePath, iImageCompressTaskListener);

                    mExecutorService.execute(imageCompressTask);**/
                }



            }
        });


    }

    private void setdata() {
        String token=commonSharedPreference.getToken();
        Call<CustomerInfo> call=RetrofitClient.getApiService().getCustomer("Bearer "+token);
        call.enqueue(new Callback<CustomerInfo>() {
            @Override
            public void onResponse(Call<CustomerInfo> call, Response<CustomerInfo> response) {
                if(response.body().getStatus().equals("success"))
                {
                    client.legalease.Model.CustomerInfo.User user=response.body().getUser();
                    et_name.setText(user.getName());
                    Log.d("state ", "onResponse: "+user.getState());
                    et_dob.setText(user.getDob());
                    et_mobile.setText(user.getMobile());
                      finalImageValue=user.getPhoto();
                    et_pincode.setText(user.getPincode());
                    et_city.setText(user.getCity());
                    et_gst.setText(user.getGstNo());
                    if (finalImageValue.equals("")||finalImageValue.equals(null)){
                        Glide.with(getApplicationContext()).load(R.drawable.male) .apply(fitCenterTransform()).into(img_addImage);
                    }
                    else
                        Glide.with(getApplicationContext()).load(finalImageValue) .apply(fitCenterTransform()).into(img_addImage);

                int satepos=0;
                int stateid=user.getState();
                    for (int i = 0; i < statelist.size(); i++) {
                        int countryid= Integer.parseInt(statelist.get(i).getCountryID());
                        if(stateid==countryid){
                            satepos=i;
                            break;
                        }
                    }
                    Log.d("satepos", "onResponse: "+user.getState());
                    etstate.setSelection(user.getState()-1);



                }
            }

            @Override
            public void onFailure(Call<CustomerInfo> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Please Check your Internet Connection ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setStatearray() {
        Call<StateModel> stateCall=RetrofitClient.getApiService().getStateList();
        stateCall.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                if(response.body()!=null){
                    statelist=response.body().getStateData();
                     String st[]=new String[statelist.size()];
                     int i=0;
                    for (StateData s:statelist
                         ) {
                        st[i]=s.getStateName();
                        i++;
                    }
                    CommonSharedPreference commonSharedPreference1=new CommonSharedPreference(UpdateActivity.this);
//                    String stateid=commonSharedPreference1.getLoginSharedPref(UpdateActivity.this).getState();
                    String statepos="";
                   for(int j=0;j<statelist.size();j++){
                       if(statelist.get(j).getCountryID().equals(state)){
                           statepos=statelist.get(j).getStateName();
                           break;
                       }
                   }
                    ArrayAdapter ad=new ArrayAdapter(UpdateActivity.this, android.R.layout.simple_spinner_item,st);


                    ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    etstate.setAdapter(ad);
                  //  int stateposid=ad.getPosition(statepos);
                  //  etstate.setSelection(stateposid);
                }
            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {

            }
        });

    }

    private void openFileSelectorDialog(int imageviewselector) {
    }


    private void sendtodb(String profile_name, String profile_dob, String profile_mobile, String profile_pincode, String profile_city, String profile_state, String profile_gst, String myToken) {
   HashMap<String ,String>data=new HashMap<>();
   data.put("name",profile_name);
   data.put("dob",profile_dob);
   data.put("state",profile_state);
   data.put("gst",profile_gst);
   data.put("city",profile_city);
   data.put("qualification","");
   data.put("pincode",profile_pincode);
//   data.put("mobile",profile_mobile);
   String token="Bearer "+myToken;
        Log.d("ua token", "sendtodb: "+token);
   Call<Updateprofilemodel> call=RetrofitClient.getApiService().customerupdate(token,data);
    call.enqueue(new Callback<Updateprofilemodel>() {
        @Override
        public void onResponse(Call<Updateprofilemodel> call, Response<Updateprofilemodel> response) {
            if(response.body()==null){
                Log.d("data", "onResponse: "+response);
            }
            else{
                Toast.makeText(UpdateActivity.this, "Profile is updated", Toast.LENGTH_SHORT).show();
              /**  Userdetails userdetails=response.body().getUserdetails();
                client.legalease.Model.VERIFYOTP.User user=new client.legalease.Model.VERIFYOTP.User();
                user.setName(userdetails.getName());
                user.setId(userdetails.getId());
                user.setToken(userdetails.getToken());
                user.setPhoto(userdetails.getPhoto());
                user.setEmail(userdetails.getEmail());
                user.setMobile(userdetails.getMobile());
                user.setCountry(String.valueOf(userdetails.getCountry()));
                user.setState(String.valueOf(userdetails.getState()));
                user.setCity(userdetails.getCity());
                user.setPincode(userdetails.getPincode());
                user.setState(userdetails.getState().toString());
                user.setGstNo(userdetails.getGstNo());
                Log.d("photo Rec", "onResponse: "+userdetails.getPhoto());
            //    commonSharedPreference.setLoginSharedPref(getApplicationContext(),user);
                Log.d("updateprofile", "onResponse: "+user);**/
              setdata();

            }
        }

        @Override
        public void onFailure(Call<Updateprofilemodel> call, Throwable t) {

        }
    });



    }


    private void launchGalleryIntent() {
        Intent intent = new Intent(UpdateActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    private void launchCameraIntent() {
        Intent intent = new Intent(UpdateActivity.this, ImagePickerActivity.class);
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
                    img_addImage.setImageURI(uri);
                 String path=getRealPathFromURI(uri);
                    uploaddoc(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode==10&& resultCode==RESULT_OK){
            Uri uri=data.getData();
            Context context= UpdateActivity.this;
            path=RealPathUtil.getRealPath(context,uri);
            Bitmap bitmap= BitmapFactory.decodeFile(path);
            img_addImage.setImageBitmap(bitmap);
            uploaddoc(path);
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            filePath = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }

    private void uploaddoc(String path) {
        progressDialog.show();
        File file=new File(path);
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part fil = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        String token="Bearer "+myToken;
        callApi.Uploadprofimage(UpdateActivity.this,fil,token);



    }


    public void saveBitmap(Bitmap bitmap) {

        File imagePath = new File("/sdcard/legaleaseProfile.jpg");
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







    public void expandableButton1(View view) {
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout1.toggle(); // toggle expand and collapse
    }

    private void initializeView() {
        back = (ImageView)findViewById(R.id.back);
        et_name = (EditText)findViewById(R.id.et_name);
        et_mobile = (EditText)findViewById(R.id.et_mobile);
 et_dob=(EditText)findViewById(R.id.et_dob);
    etstate=(Spinner)findViewById(R.id.et_state);
        et_pincode = (EditText)findViewById(R.id.et_pincode);
        et_city = (EditText)findViewById(R.id.et_city);
et_gst=(EditText)findViewById(R.id.et_gst);
        linear_addImageFromGallery = (LinearLayout)findViewById(R.id.linear_addImageFromGallery);
        linear_addImageFromCamera = (LinearLayout)findViewById(R.id.linear_addImageFromCamera);
        img_addImage = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.profile_image);
        iv_check = (ImageView)findViewById(R.id.iv_check);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(UpdateActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
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

            File file = new File(finalMediaPath);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);
            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), profile_name);
            RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), profile_address);
            RequestBody mobile = RequestBody.create(MediaType.parse("multipart/form-data"), profile_mobile);
            RequestBody pincode = RequestBody.create(MediaType.parse("multipart/form-data"), profile_pincode);
            RequestBody city = RequestBody.create(MediaType.parse("multipart/form-data"), profile_city);
            RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"), profile_state);
            RequestBody country = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), myToken);


            callApi.upDateProfile(UpdateActivity.this, photo, name,address,mobile,pincode,city,state, token, country);

//            img_addImage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
        }

        @Override
        public void onError(Throwable error) {
            //very unlikely, but it might happen on a device with extremely low storage.
            //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
            Log.wtf("ImageCompressor", "Error occurred", error);
        }
    };



    public void Response_uploadProfile(UserUpdate responce) {
        progressDialog.dismiss();

            Log.d("Sdfhbsd", String.valueOf(responce));

            if (responce.getStatus().equals("success")) {
                String myToken = responce.getUser().get(0).getToken();
                int id = responce.getUser().get(0).getId();
                String userName = responce.getUser().get(0).getName();
                String address= responce.getUser().get(0).getAddress();
                String userMobile = responce.getUser().get(0).getMobile();
                String pinCode = responce.getUser().get(0).getPincode();
                String photo = responce.getUser().get(0).getPhoto();
                String city = responce.getUser().get(0).getCity();
                String state = responce.getUser().get(0).getState();




                userdata = new User();
                userdata.setToken(myToken);
                userdata.setId(Integer.valueOf(id));
                userdata.setPhoto(photo);
                userdata.setName(userName);
                userdata.setAddress(address);
                userdata.setMobile(userMobile);
                userdata.setPincode(pinCode);
                userdata.setCity(city);
                userdata.setState(state);


                Log.d("dsfbf", String.valueOf(userdata));
//                commonSharedPreference.setLoginSharedPref(this, userdata);
                Log.d("sdfbvhgdsf", String.valueOf(commonSharedPreference));



            }

        finish();
        Toast.makeText(getApplicationContext(),"Profile Uploaded Successful",Toast.LENGTH_LONG).show();


    }


    public void uploaddoc(Uploadimageprofile body) {
        if(body!=null){
            progressDialog.dismiss();

        }
        else
        {
            progressDialog.dismiss();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        profile_state=statelist.get(position).getCountryID();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
