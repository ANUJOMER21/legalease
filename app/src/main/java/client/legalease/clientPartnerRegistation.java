package client.legalease;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import client.legalease.Model.ClientOrderModel;
import client.legalease.Model.PartnerModel.Partnermodel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class clientPartnerRegistation extends AppCompatActivity {
private TextView name,mobile,service,date;
private EditText message;
private Button submit;
private String nameclient="",token="",clientmobile="",servicename="",assid="";
int clientid=0;
LinearLayout llmob;
private CommonSharedPreference commonSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_partner_registation);
        submit=findViewById(R.id.update);
        name=findViewById(R.id.clientname);
        mobile=findViewById(R.id.clientnumber);
        service=findViewById(R.id.clientsereq);
        llmob=findViewById(R.id.llmob);
        llmob.setVisibility(View.GONE);
        date=findViewById(R.id.clientsereqdate);
        message=findViewById(R.id.clientmessage);

        Intent i=getIntent();
servicename=i.getStringExtra("service");
assid=i.getStringExtra("assid");

        commonSharedPreference = new CommonSharedPreference(this);

        try{
            if(commonSharedPreference.getLoginSharedPref(getApplicationContext())!=null){
                nameclient=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getName();
                clientid=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();
                token=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                clientmobile=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getMobile();

            }

        }catch (NullPointerException ignore){}
         catch (IndexOutOfBoundsException ignore){}
        name.setText(nameclient);
        mobile.setText(clientmobile);
        service.setText(servicename);
        Date c = Calendar.getInstance().getTime();
        String formattedDate="";

        SimpleDateFormat df = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate  = df.format(c);
        }
        date.setText(formattedDate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             senddata();

                Log.d("submit", "onClick: "+"clicked");


            }
        });


    }

    private void senddata() {
      if(message.getText().toString().isEmpty()){
          Toast.makeText(this, "Enter Some Message", Toast.LENGTH_SHORT).show();
      }
      else {     submit.setVisibility(View.INVISIBLE);
                  String token1="Bearer "+token;
          HashMap<String,String> hashMap=new HashMap<>();
          hashMap.put("associate_id",assid);
          hashMap.put("message",message.getText().toString());
          Call<ClientOrderModel> clientOrderModelCall= RetrofitClient.getApiService().getClientorderRequest(token1,hashMap);
          clientOrderModelCall.enqueue(new Callback<ClientOrderModel>() {
              @Override
              public void onResponse(Call<ClientOrderModel> call, Response<ClientOrderModel> response) {
//                  assert response.body() != null;
                  if (response.body() == null) {
                      Log.d("reg", "onResponse: " + response+token1);
                  } else {
                      if (response.body().getStatus().equals("success")) {
                          //Toast.makeText(clientPartnerRegistation.this, "Your service request has been send successfully.", Toast.LENGTH_SHORT).show();
                         showAlert(clientPartnerRegistation.this,"Your Request is successfully submitted. You can see it on My Request Page");
                        //  finish();

                      } else {
                          Toast.makeText(clientPartnerRegistation.this, "Your service request already send successfully", Toast.LENGTH_SHORT).show();
                          finish();
                      }
                  }
              }

              @Override
              public void onFailure(Call<ClientOrderModel> call, Throwable t) {
                  Log.d("orderfail", "onFailure: "+t.toString());

              }
          });
      }

    }
    public static void showAlert(Activity activity, String message) {
        TextView title = new TextView(activity);

        title.setText("Success ");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                activity.finish();
            }
        });

        AlertDialog alert = builder.show();
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        messageText.setTextColor(Color.RED);
    }

}