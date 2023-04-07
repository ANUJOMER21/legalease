package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.CloseActivityDetail;
import client.legalease.Model.OrderModel.OrderData;
import client.legalease.OpenActivityDetail;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

public class OrderAdapterClosed extends RecyclerView.Adapter<OrderAdapterClosed.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<OrderData> orderData;

    private Context context;

    public OrderAdapterClosed(Context context, List<OrderData> orderData) {
        this.context = context;
        this.orderData = orderData;
    }



    @NonNull
    @Override
    public OrderAdapterClosed.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);


        return new OrderAdapterClosed.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapterClosed.ViewHolder viewHolder, int i) {

        String order="";
        String  company="";



        try {
            order = orderData.get(i).getOrderId();
            company = orderData.get(i).getService().getTitle();



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

                view_order.setText("View In-voice");




                view_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        OrderData myData  =null;
                        String company = "";
                        String orderId = "";
                        String order = "";
                        try {
                            myData  =orderData.get(pos);
                            company = myData.getService().getTitle();
                            orderId = String.valueOf(myData.getId());
                            order = myData.getOrderId();
                        }catch (NullPointerException ignored){

                        }catch (IndexOutOfBoundsException ignored){

                        }
                        Intent intent  =new Intent(context, CloseActivityDetail.class);
                        intent.putExtra("company", company);
                        intent.putExtra("orderId", orderId);
                        intent.putExtra("order", order);


                        context.startActivity(intent);
                    }
                });


            }

        }
    }


