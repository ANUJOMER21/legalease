package client.legalease;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import client.legalease.BroadcastReceiver.ConnectivityReceiver;
import client.legalease.BroadcastReceiver.MyApplication;
import client.legalease.Fragment.AssociateFragment;
import client.legalease.Fragment.FragmentHome;
import client.legalease.Fragment.MyOrder;
import client.legalease.Fragment.Service_offer_fragment;
import client.legalease.Fragment.Service_request_fragment;
import client.legalease.Fragment.ServicesFragment;
import client.legalease.Model.LoginModel.LoginModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.CallApi;

import static client.legalease.BillUploadActivty.REQUEST_PERMISSIONS;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener, LocationListener {

    CommonSharedPreference commonSharedPreference;
    TextView tv_scanIn, tv_scanOut, tv_details;
    String content;
    String intime = "";
    String outtime = "";
    LocationManager locationManager;
    String current_address;
    int id = 0;
    String token = "";
    String name = "";
    String email = "";
    String id_cms_privileges = "";
    String mobile = "";
    String country = "";
    String state = "";
    String city = "";
    String pincode = "";
    String address = "";
    String qualification = "";
    String photo = "";
    String buttonclick = "";

    ProgressDialog pDialog;
    CallApi callApi = new CallApi();
    TextView tv_userName, tv_companyName, tv_inTime;
    de.hdodenhof.circleimageview.CircleImageView iv_userImage;
    ProgressBar progressBar;
    LinearLayout linearclient;
    TextView tv_hint;
    SharedPreferences prefs_registration;
    String clientName = "";
    String clientImage = "";
    String clientCompanyName = "";
    String clientEntryDate = "";
    String idRequiredForTimeOut = "";
    String selectionId = "";
    ProgressDialog pDialogDialog;


    TextView loggedInUserName, loggedInUserEmail;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    String finalImageValue = "";

    String checkInternetConnection;
    boolean pressBackToExit = false;
    Dialog dialog;


    Fragment fragment = null;
    int idi = 0;
    String checkSum = "";
    String fragChange = "0";
    LinearLayout linear_search;
    boolean boolean_permission;
    String lat = "";
    String lng = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        commonSharedPreference = new CommonSharedPreference(this);

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();
                name = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getName();
                email = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getEmail();
                id_cms_privileges = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getIdCmsPrivileges();
                mobile = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getMobile();


            }

        } catch (NullPointerException ignored) {

        } catch (IndexOutOfBoundsException ignore) {

        }

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;



        }

        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))) {
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;



        }




        //getLatLng();
        try {
            Intent intent = getIntent();
            fragChange = intent.getStringExtra("fragChange");

        } catch (NullPointerException ignored) {
            fragChange = "0";

        }


        try {
            if (fragChange.equals("0") || fragChange.equals(null)) {
                loadFragment(new FragmentHome());
                loadFragment(new FragmentHome());
            } else {
                loadFragment(new MyOrder());

            }

        } catch (NullPointerException ignored) {
            loadFragment(new FragmentHome());

        }


        LinearLayout linear_search = (LinearLayout) findViewById(R.id.linear_search);
        linear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        prefs_registration = PreferenceManager.getDefaultSharedPreferences(this);
        clientName = prefs_registration.getString("clientName", "");
        clientImage = prefs_registration.getString("clientProfileUrl", "");
        clientCompanyName = prefs_registration.getString("clientCompanyName", "");
        clientEntryDate = prefs_registration.getString("clintIntime", "");
        buttonclick = prefs_registration.getString("buttonclick", "");


        View header = navigationView.getHeaderView(0);
        loggedInUserName = (TextView) header.findViewById(R.id.loggedInUserName);
        loggedInUserEmail = (TextView) header.findViewById(R.id.loggedInUserEmail);

        imageView = (de.hdodenhof.circleimageview.CircleImageView) header.findViewById(R.id.imageView);
       String name= commonSharedPreference.getname();
        loggedInUserName.setText(name);
        String email=commonSharedPreference.getemail();
        loggedInUserEmail.setText(email);

          finalImageValue=commonSharedPreference.getProfilephoto(HomeActivity.this);
        Log.d("profileimage", "onCreate: "+finalImageValue);
        if (finalImageValue.equals("") || finalImageValue.equals(null)) {
            Glide.with(getApplicationContext()).load(R.drawable.male).apply(fitCenterTransform()).into(imageView);
        } else
            Glide.with(getApplicationContext()).load(finalImageValue).apply(fitCenterTransform()).into(imageView);


    }

    private void getLatLng() {
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean loadFragment(Fragment fragment) {

        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


         idi = item.getItemId();
      if (idi == R.id.nav_myAccount) {
            if (ConnectivityReceiver.isConnected() == true) {
                if(fragment instanceof  FragmentHome){}
                else {
                fragment = new FragmentHome();}
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        } else if (idi==R.id.nav_services) {
            if (ConnectivityReceiver.isConnected() == true) {
                fragment = new ServicesFragment();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }else if (idi==R.id.nav_myOrder) {
            if (ConnectivityReceiver.isConnected() == true) {
                fragment = new MyOrder();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }


      }
      /**else if (idi==R.id.nav_network) {
          if (ConnectivityReceiver.isConnected() == true) {
              fragment = new MyNetwork();
          } else {
              Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
          }

      }else if (idi==R.id.nav_easy_money) {
          if (ConnectivityReceiver.isConnected() == true) {
              fragment = new EasyMoney();
          } else {
              Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
          }

        }else if (idi==R.id.nav_billing) {
            if (ConnectivityReceiver.isConnected() == true) {
                fragment = new BillingFragment();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }else if (idi==R.id.nav_wallet) {
            if (ConnectivityReceiver.isConnected() == true) {
                fragment = new WalletFragment();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }
         **/else if (idi == R.id.nav_profile) {
            Intent intent = new Intent(HomeActivity.this, UpdateActivity.class);
            startActivity(intent);
        }
        /**else if (idi==R.id.nav_refer) {
            if (ConnectivityReceiver.isConnected() == true) {
                fragment = new ReferFragment();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }
         **/
      else if (idi==R.id.nav_service_request) {
         fragment=new Service_request_fragment();

      }
      else if (idi==R.id.nav_offer) {
         fragment=new  Service_offer_fragment();

      }
      else if (idi==R.id.nav_update) {
          if (ConnectivityReceiver.isConnected() == true) {
              fragment = new UpdateFragment();
          } else {
              Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
          }

      }else if (idi==R.id.nav_associate) {
          if (ConnectivityReceiver.isConnected() == true) {
              if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
              }else{
              //   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
              //    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                  fragment = new AssociateFragment();

              }
          } else {
              Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
          }
          } else if (idi==R.id.nav_changePassword) {
            if (ConnectivityReceiver.isConnected() == true) {
                changePassword();
            } else {
                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }

        }else if (idi == R.id.logout) {
      Dialog dialog1=new Dialog(HomeActivity.this);
      dialog1.setContentView(R.layout.logoutdialog);
      Button yes=dialog1.findViewById(R.id.yes);

          Button no=dialog1.findViewById(R.id.no);
          no.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dialog1.dismiss();
              }
          });
          yes.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                dialog1.dismiss();
                  logout();
              }
          });
dialog1.show();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());
        return loadFragment(fragment);
    }

    private void logout() {
        commonSharedPreference.setLoggedin(false);
        commonSharedPreference.setProfilephoto(HomeActivity.this,"");
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();

    }

    private void changePassword() {

          dialog = new Dialog(HomeActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.show();
//        TextView tv = (TextView)dialog.findViewById(R.id.tv);
//        String invitation_text =("Please select an option to send invitation to :- \n");
//        tv.setText(invitation_text+ name);

        final EditText et_password = (EditText) dialog.findViewById(R.id.et_password);
       final EditText et_newPassword = (EditText)dialog.findViewById(R.id.et_newPassword);
       final EditText et_confirmPpassword = (EditText)dialog.findViewById(R.id.et_confirmPpassword);
        Button chane_password = (Button)dialog.findViewById(R.id.chane_password);
        ImageView img_close = (ImageView)dialog.findViewById(R.id.img_close);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        chane_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldpassword = "";
                String newpassword = "";
                String cpassword = "";

                try {
                    oldpassword = et_password.getText().toString().trim();
                    newpassword = et_newPassword.getText().toString().trim();
                    cpassword = et_confirmPpassword.getText().toString().trim();
                    if (oldpassword.equals("")||oldpassword.equals(null)){
                        et_password.setError("Please enter current password");
                        et_password.requestFocus();
                    }else if (newpassword.equals("")||newpassword.equals(null)){
                        et_newPassword.setError("Please enter your New Password");
                        et_newPassword.requestFocus();
                    }else if (cpassword.equals("")||cpassword.equals(null)){
                        et_confirmPpassword.setError("Please enter the password again for confirmation");
                        et_confirmPpassword.requestFocus();
                    }else if (!newpassword.equals(cpassword)){
                        Toast.makeText(getApplicationContext(),"In order to continue you must enter the same password",Toast.LENGTH_SHORT).show();
                    }else {
                        pDialogDialog = new ProgressDialog(HomeActivity.this);
                        String pleaseWait = getResources().getString(R.string.please_wait);

                        pDialogDialog.setMessage(pleaseWait);
                        pDialogDialog.setIndeterminate(false);
                        pDialogDialog.setCancelable(false);
                        pDialogDialog.show();
                        HashMap<String,String> changePasswordCredential = new HashMap<>();
                        changePasswordCredential.put("token",token);
                        changePasswordCredential.put("oldpassword",oldpassword);
                        changePasswordCredential.put("newpassword",newpassword);
                        changePasswordCredential.put("cpassword",cpassword);


                        try {
                            callApi.requestChangePassword(HomeActivity.this, changePasswordCredential);
                        } catch (Exception e) {
                            e.getStackTrace();
                        }

                    }
                }catch (NullPointerException ignored){

                }catch (Exception ignore){

                }
            }
        });


    }


    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }



    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }


        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();

        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    public void clossDialog() {
        pDialogDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Please enter correct details",Toast.LENGTH_SHORT).show();
    }

    public void Response_ChangePassword(LoginModel body) {
        pDialogDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Your Password has been successfully Changed !!!",Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }



    @Override
    public void onBackPressed() {
        if (idi == 0) {
            if (pressBackToExit) {
                super.onBackPressed();
                return;
            }
            this.pressBackToExit = true;
            Toast.makeText(getApplicationContext(),"Please click back again to exit",Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pressBackToExit=false;
                    finishAffinity();
                }
            }, 5000);
        } else {
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }
    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(getApplicationContext(),"Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(),Toast.LENGTH_SHORT).show();

        lat = String.valueOf(location.getLatitude());
        lng = String.valueOf(location.getLongitude());
    }


    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public String getLattitude() {
        return lat;
    }
    public String getLongitude() {
        return lng;
    }

    public String getToken() {
        return token;
    }
}