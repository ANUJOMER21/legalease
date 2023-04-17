package client.legalease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.Model.PartnerModel.Datum;
import client.legalease.Model.PartnerModel.Service;
import de.hdodenhof.circleimageview.CircleImageView;

public class PartnerDetail extends AppCompatActivity {
CircleImageView partner_image;
ImageView back,map;
TextView name,email,address,Qualification,Service_offered,partner_des;
Button bookMeeting;
RelativeLayout viewpagerServiceFragment;
    int mYear;
    int mMonth;
    int mDay;
    String date_time="";
    String service,assid;
    LinearLayout lladd,llemail;


    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        Gson gson=new Gson();
        Intent i=getIntent();

        service=i.getStringExtra("service");
        assid=i.getStringExtra("assid");
        String data=i.getStringExtra("partner_detail");
        Datum partnerDetail=gson.fromJson(data, Datum.class);
        String from=i.getStringExtra("from");

        Log.d("assid2", "onCreate: "+assid);
        init_view();
        map.setVisibility(View.GONE);
        lladd.setVisibility(View.GONE);
       // llemail.setVisibility(View.GONE);
        if(from.equals("1")){
            bookMeeting.setText("book meet");
        }
        setData(partnerDetail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       bookMeeting.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           if(!from.equals("1")){
               bookMeet();}
           else{
bookmeet2();
           }
           }
          
       });
       map.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String mapuri = "http://maps.google.co.in/maps?q=" + address.getText().toString();
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapuri));
              startActivity(intent);


           }
       });


    }

    private void bookmeet2() {




    }

    private void bookMeet() {
Intent i=new Intent(getApplicationContext(),clientPartnerRegistation.class);

        i.putExtra("service",service);
        i.putExtra("assid",assid);
        startActivity(i);
finish();


    }

    private void setData(Datum partnerDetail) {
        String Imageurl=ApiConstant.IMAGEURL+partnerDetail.getPhoto();
        Glide.with(this).load(Imageurl).into(partner_image);
        name.setText(partnerDetail.getName());
        email.setText(partnerDetail.getCompanyName());
String add="";
if(partnerDetail.getAddress()!=null){
    add=add+partnerDetail.getAddress()+", ";
}
if(partnerDetail.getCity()!=null){
    add=add+partnerDetail.getCity()+", ";
}
        if(partnerDetail.getPincode()!=null){
            add=add+partnerDetail.getPincode()+" ";
        }
        address.setText(add);
        if(partnerDetail.getQualification()!=null){
            Qualification.setText(partnerDetail.getQualification().toString());
        }
        else {
            Qualification.setText("");
        }
        if(!partnerDetail.getService().isEmpty()){
            String services=getService(partnerDetail.getService());
            Service_offered.setText("Services Offered: "+services);
        }
        else {
            Service_offered.setText("");
        }
    //    Service_offered.setText(partnerDetail.getSummary().toString());

if(partnerDetail.getSummary()!=null){
    partner_des.setText(HtmlCompat.fromHtml(partnerDetail.getSummary(),0));
}
else {
    partner_des.setText("");
}

    }

    private String getService(List<Service> service) {
        String ser="";
        for (Service service1:service
             ) {
            ser=ser+service1.getTitle()+", ";

        }

        return ser;

    }

    private void init_view() {
        partner_image=findViewById(R.id.partner_image);
        name=findViewById(R.id.partner_name);
        email=findViewById(R.id.partner_email);
        address=findViewById(R.id.partner_address);
        Qualification=findViewById(R.id.partner_quali);
        Service_offered=findViewById(R.id.partner_service);
        back=findViewById(R.id.back);
        bookMeeting=findViewById(R.id.btn_book_meet);
        viewpagerServiceFragment=findViewById(R.id.viewpagerServiceFragment);
        map=findViewById(R.id.map);
        partner_des=findViewById(R.id.partner_desc);
        lladd=findViewById(R.id.lladdress);
        llemail=findViewById(R.id.llemail);
    }
}