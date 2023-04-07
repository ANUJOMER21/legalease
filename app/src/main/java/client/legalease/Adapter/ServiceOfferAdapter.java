package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import client.legalease.Model.ServiceOfferModel.Datum3;
import client.legalease.R;
import client.legalease.ServiceOfferDetail;
import client.legalease.Service_offer;
import client.legalease.Status;

public class ServiceOfferAdapter extends RecyclerView.Adapter<ServiceOfferAdapter.ViewHolder> {
    Context context;
    List<Datum3> datum3List;
int num;
    public ServiceOfferAdapter(Context context, List<Datum3> datum3List, int i) {
        this.context = context;
        this.datum3List = datum3List;
        num=i;
    }

    @NonNull
    @Override
    public ServiceOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.serviceofferview,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceOfferAdapter.ViewHolder viewHolder, int i) {
        String name="",date="",paymentst;

        try {
            name=datum3List.get(i).getAssoicate().getName();
            paymentst=datum3List.get(i).getStatus();
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datum3List.get(i).getCreatedAt());
            date=new SimpleDateFormat("dd-MM-yyyy").format(date1);
            viewHolder.name1.setText(name);
            viewHolder.date1.setText(date);
           paymentst=new Status().getStatus(Integer.valueOf(paymentst));
            viewHolder.paymentstatus.setText(paymentst);

        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return datum3List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name1,date1,paymentstatus;
        CardView serviceoffercv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name1=itemView.findViewById(R.id.partner_name);
            date1=itemView.findViewById(R.id.tv_date);
            serviceoffercv=itemView.findViewById(R.id.serviceoffercv);
            paymentstatus=itemView.findViewById(R.id.paymentstatus);
            serviceoffercv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Gson gson=new Gson();
            int position=getAdapterPosition();
            String data=gson.toJson(datum3List.get(position));
            Intent i=new Intent(context, ServiceOfferDetail.class);
            i.putExtra("data",data);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            if(context instanceof Service_offer)
            {
               // ((Service_offer)context).finish();
            }
        }
    }
}
