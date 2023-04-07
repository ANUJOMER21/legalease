package client.legalease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import client.legalease.Adapter.OrderDetailPageAdapter;
import client.legalease.Model.Acceptedordermodel.Datum;
import client.legalease.Model.Acceptedordermodel.OrderService;

public class OrderDetailpage extends AppCompatActivity {
ImageView back;
TextView tv_order,tv_company,partner_name,orderstatus,amount,ordercreatedate,orderacceptdate;
RecyclerView servicereq;
    Datum datum=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detailpage);
    initview();
    String data=getIntent().getStringExtra("data");
        Gson gson=new Gson();
        datum=gson.fromJson(data,Datum.class);

      try {
            setData(datum);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Log.d("orderdetail", "onCreate: "+datum.getCreatedAt());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData(Datum datum) throws ParseException {
tv_order.setText(String.valueOf(datum.getId()));
tv_company.setText(datum.getAssoicate().getCompanyName());
partner_name.setText(datum.getAssoicate().getName());
String sta=new Status().getStatus(Integer.parseInt(datum.getStatus()));
orderstatus.setText((sta));
amount.setText("₹ "+String.valueOf(datum.getAmount()));
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datum.getCreatedAt());
      String  createdate = new SimpleDateFormat("dd-MM-yyyy").format(date1);
Date date2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datum.getOrderCompleteAt());
        String  acceptat = new SimpleDateFormat("dd-MM-yyyy").format(date2);

        ordercreatedate.setText(createdate);
        orderacceptdate.setText(acceptat);
        List<OrderService> orderServiceList=new ArrayList<>();
        orderServiceList=datum.getOrderServiceList();
        Log.d("list", "setData: "+orderServiceList.size());
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        servicereq.setLayoutManager(manager);
        OrderDetailPageAdapter pageAdapter=new OrderDetailPageAdapter(orderServiceList,OrderDetailpage.this);
        servicereq.setAdapter(pageAdapter);
        pageAdapter.notifyDataSetChanged();

    }

    private void initview() {
    back=findViewById(R.id.back);
    tv_order=findViewById(R.id.tv_order);
        tv_company=findViewById(R.id.tv_company);
        partner_name=findViewById(R.id.partner_name);
        orderstatus=findViewById(R.id.partner_email);
        amount=findViewById(R.id.partner_amount);
        orderacceptdate=findViewById(R.id.partner_acceptdate);
        ordercreatedate=findViewById(R.id.partner_createdate);
        servicereq=findViewById(R.id.rv_servicelist);


    }
}