package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import client.legalease.CloseActivityDetail;
import client.legalease.Model.Acceptedordermodel.Datum;
import client.legalease.Model.Acceptedordermodel.OrderService;
import client.legalease.Model.OrderModel.OrderData;
import client.legalease.OpenActivityDetail;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<Datum> orderData;

    private Context context;
    String selector = "";

    public OrderAdapter(Context context, List<Datum> orderData, String selector) {
        this.context = context;
        this.orderData = orderData;
        this.selector = selector;
    }



    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);


        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder viewHolder, int i) {

        String order="";
        String  company="";



        try {
            List<OrderService> orderServiceList=new ArrayList<>();
            orderServiceList=orderData.get(i).getOrderServiceList();
            if(orderServiceList.size()!=0) {
                order = String.valueOf(orderServiceList.get(0).getOrderId());


            }
            company = orderData.get(i).getAssoicate().getName();

        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }
        viewHolder.tv_order.setText(order);
        viewHolder.tv_company.setText(company);


    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order,tv_company,tv_hint;
        Button view_order;
        Typeface font2 = Typeface.createFromAsset(context.getAssets(),"AbrilFatface-Regular.otf");
        Typeface font1 = Typeface.createFromAsset(context.getAssets(),"Jipatha-Regular.ttf");





        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_order = (TextView) itemView.findViewById(R.id.tv_order);
            tv_company = (TextView) itemView.findViewById(R.id.tv_company);
            view_order = (Button) itemView.findViewById(R.id.view_order);
            tv_hint = (TextView) itemView.findViewById(R.id.tv_hint);


            tv_order.setTypeface(font1);
            tv_company.setTypeface(font1);
            view_order.setTypeface(font2);
            tv_hint.setTypeface(font1);
            if (selector.equals("1")) {
                view_order.setText("View Order");
            } else {
                view_order.setText("View In-voice");


            }


            view_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Datum myData = null;
                    String company = "";
                    String orderId = "";
                    String serviceid = "";
                    String s = "";
                    String price = "";
                    String id = "";
                    String industryType = "";
                    String gov_fee = "";
                    String professional_fee = "";
                    try {
                        myData = orderData.get(pos);
                        company = orderData.get(pos).getAssoicate().getName();
                        if(orderData.get(pos).getOrderServiceList().size()!=0){
                        orderId =String.valueOf(orderData.get(pos).getOrderServiceList().get(0).getOrderId());}

                        id = String.valueOf(myData.getId());

                        serviceid = myData.getServiceId().toString();
                        s = myData.getAmount().toString();
                        price = s.substring(0, s.length() - 2);

                    } catch (NullPointerException ignored) {

                    } catch (IndexOutOfBoundsException ignored) {

                    }
                    Gson gson=new Gson();
                    String dat= gson.toJson(myData);
                    Intent intent = new Intent(context, OpenActivityDetail.class);
                    intent.putExtra("company", company);
                    intent.putExtra("data", dat);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("serviceid", serviceid);
                    intent.putExtra("price", price);
                    intent.putExtra("id", id);
                    intent.putExtra("industryType", industryType);
                    intent.putExtra("gov_fee", gov_fee);
                    intent.putExtra("professional_fee", professional_fee);
                  //  Toast.makeText(context, id, Toast.LENGTH_SHORT).show();

                    context.startActivity(intent);


                }
            });
        }
        }

}
