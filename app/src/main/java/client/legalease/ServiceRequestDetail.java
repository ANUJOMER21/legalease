package client.legalease;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;

import client.legalease.Model.Bookmeetmodel;
import client.legalease.Model.Servicerequestmodel.Datum2;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.Manifest.permission.CALL_PHONE;
public class ServiceRequestDetail extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
TextView name,email,number,message;
Button Bookmeet;
ImageView back;
String Date,Time;
    Datum2 datum2;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    ImageView phone;
LinearLayout llmo;
EditText remark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_detail);
        initView();


        Gson gson=new Gson();
        Intent intent=getIntent();
        String data=getIntent().getStringExtra("data");
         datum2=gson.fromJson(data,Datum2.class);
        setData(datum2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bookmeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ServiceRequestDetail.this,ServiceRequestDetail.this,year,month,day);
           datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        phone.setOnClickListener(arg ->{
            String phone_num=number.getText().toString();
            Intent intent1=new Intent(Intent.ACTION_DIAL);
            intent1.setData(Uri.parse("tel:"+phone_num));

            startActivity(intent1);
        });
    }

    private void setData(Datum2 datum2) {
        name.setText(datum2.getAssoicate2().getName());
        if(datum2.getAssoicate2().getCompanyName()!=null){
        email.setText((CharSequence) datum2.getAssoicate2().getCompanyName());}

        number.setText(datum2.getAssoicate2().getMobile());
        message.setText(datum2.getMessage());
    }

    private void initView() {
        phone=findViewById(R.id.phone);
        name=findViewById(R.id.partner_name);
        email=findViewById(R.id.partner_email);
        number=findViewById(R.id.partner_number);
        message=findViewById(R.id.message);
        llmo=findViewById(R.id.llmo);
        Bookmeet=findViewById(R.id.btn_book_meet);
        back=findViewById(R.id.back);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ServiceRequestDetail.this, ServiceRequestDetail.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        Date=myYear+"-"+myMonth+"-"+myday;
        Time=myHour+"-"+myMinute;
        bookmeet(Date,Time);
    }
    TextView okay_text, cancel_text;
    private void bookmeet(String date, String time) {
       // HashMap<String ,String> meetdetail=new HashMap<>();
        Dialog dialog=new Dialog(ServiceRequestDetail.this);
        dialog.setContentView(R.layout.meetingdialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        okay_text = dialog.findViewById(R.id.okay_text);
         cancel_text = dialog.findViewById(R.id.cancel_text);
        remark=dialog.findViewById(R.id.message);
        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String ,String>data=new HashMap<>();
                if(remark.getText().toString().isEmpty()){
                    Toast.makeText(ServiceRequestDetail.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
                else {
                    data.put("associate_id", String.valueOf(datum2.getAssociateId()));
                    data.put("meeting_date", date);
                    data.put("meeting_time", time);
                    data.put("remarks", remark.getText().toString());
                    sendData(data);
                    dialog.dismiss();
                }
            }
        });
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog.dismiss();
            }
        });
dialog.show();
    }

    private void sendData(HashMap<String, String> data) {
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(ServiceRequestDetail.this);
        String token="Bearer"+commonSharedPreference.getToken();
        Log.d("mmeting", "sendData: run");
        Call<Bookmeetmodel> call= RetrofitClient.getApiService().bookmeet(token,data);
        call.enqueue(new Callback<Bookmeetmodel>() {
            @Override
            public void onResponse(Call<Bookmeetmodel> call, Response<Bookmeetmodel> response) {
                if(response.body()!=null){
                    Log.d("mmeting", "sendData: "+response.body());
                    Toast.makeText(ServiceRequestDetail.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("response", "onResponse: "+response);
                }
            }

            @Override
            public void onFailure(Call<Bookmeetmodel> call, Throwable t) {
                Log.d("mmeting", "sendData: "+t.toString());
            }
        });
    }
}