package client.legalease;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.Model.NewPartnerModel.Assoicate;
import client.legalease.Model.NewPartnerModel.PartnerModel2;
import client.legalease.Model.PartnerModel.Datum;
import client.legalease.Model.PartnerModel.Partnermodel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import client.legalease.WebServices.ApiService;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.http2.Header;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import client.legalease.Adapter.viewallPartnerAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllPartner extends AppCompatActivity {
    ImageView back;
    private static final int REQUEST_LOCATION = 1;
    RecyclerView rv_all_partner;
    ProgressBar progressBar;
    List<Assoicate> datumList;
private LocationManager locationManager;
    TextView lattv, lontv;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

int page=1;
int lastpage=1;

    private String id, name, price, servicename, lat, lon;

private TextView errortxt;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_partner);
        initview();
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        servicename = intent.getStringExtra("servicename");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Log.d("viewall", "onCreate: run");

        datumList = new ArrayList<>();

        try {
            getallpartner(page);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getallpartner(final int page) throws IOException {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 5, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                addpartner(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            }
        });
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    CommonSharedPreference commonSharedPreference = new CommonSharedPreference(getApplicationContext());
                    commonSharedPreference.setlocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                   lat= String.valueOf(location.getLatitude());
                   lon= String.valueOf(location.getLongitude());
                    Log.d("startlocation", "______________________________________________________________________________");
                    Log.d("flclocation", "getallpartner: "+location.getLongitude()+"|"+location.getLatitude());
               //     addpartner(lat,lon);
                }
            }
        });
        Log.d("api1", "run");

rv_all_partner.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_all_partner, new RecyclerTouchListener.ClickListener() {
    @Override
    public void onClick(View view, int position) {
        //finish();
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}));
    }

    private void initview() {
        errortxt=findViewById(R.id.errortxt);
        back = findViewById(R.id.back);
        lattv=findViewById(R.id.lat);
        lontv=findViewById(R.id.lon);
        rv_all_partner = findViewById(R.id.rv_viewallpartner);
        progressBar = findViewById(R.id.progressBar_viewall);

    }
    public void addpartner(String lat,String lon){

        CommonSharedPreference commonSharedPreference = new CommonSharedPreference(this);
        Log.d("chplocation", "getallpartner: "
                //+commonSharedPreference.getlon()+"|"+commonSharedPreference.getlat()+
                +lon+"|"+lat+
                "| id "+id);
        Log.d("endlocation", "______________________________________________________________________________");
        String token = "Bearer " + commonSharedPreference.getToken();
        Log.d("vaptoken", "getallpartner: " + token);
        Call <PartnerModel2> call = RetrofitClient.getApiService().getAssociateList(String.valueOf(page),token,
              //  commonSharedPreference.getlat(),commonSharedPreference.getlon()
                lat,lon
                ,id);
        Log.d("api url", "getallpartner: "+call.request().url());
        call.enqueue(new Callback < PartnerModel2>() {
            @Override
            public void onResponse(Call < PartnerModel2 > call, Response < PartnerModel2 > response) {
                if (response.body() == null) {
                  errortxt.setVisibility(View.VISIBLE);
                  progressBar.setVisibility(View.GONE);
                    //  Toast.makeText(ViewAllPartner.this, "null ", Toast.LENGTH_SHORT).show();
                }
                if(response.body().getAssoicate().size()==0){
                  progressBar.setVisibility(View.GONE);
                    errortxt.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Log.d("api2", "onResponse: " + response.body().getAssoicate().get(0).getAddress());
                    try {

                    } catch (NullPointerException ignore) {
                    } catch (IndexOutOfBoundsException ignore) {
                    }
                    Long lp;
                    datumList=response.body().getAssoicate();
                    ///   pagenum.setText(response.body().getAssoicate().getCurrentPage().toString());
                    Log.d("lastpage", "onResponse: " + lastpage);
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    //      GridLayoutManager manager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                    rv_all_partner.setLayoutManager(manager);
                    viewallPartnerAdapter viewallPartnerAdapter = new viewallPartnerAdapter(getApplicationContext(), datumList,servicename);
                    rv_all_partner.setAdapter(viewallPartnerAdapter);
                    viewallPartnerAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call < PartnerModel2 > call, Throwable throwable) {
                Log.d("viewallassociate", "onFailure: " + throwable.toString());
            }
        });
    }


        }

