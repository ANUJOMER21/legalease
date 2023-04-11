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

import client.legalease.Model.Servicerequestmodel.Datum2;
import client.legalease.Model.customerRequestListModel.Datum;
import client.legalease.R;
import client.legalease.ServiceOfferDetail;
import client.legalease.ServiceRequestDetail;
import client.legalease.Status;

public class GetServiceRequestAdapter extends RecyclerView.Adapter<GetServiceRequestAdapter.ViewHolder> {
      Context context;
      List<Datum>list;


    public GetServiceRequestAdapter(Context context, List<Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GetServiceRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.servicerequestview,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetServiceRequestAdapter.ViewHolder viewHolder, int i) {
        String name="",date="",stat;
        try {


if(list.get(i).getAssoicate()!=null) {
    name = list.get(i).getAssoicate().getName();
    Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(list.get(i).getCreatedAt());
    date = new SimpleDateFormat("dd-MM-yyyy").format(date1);
stat=new Status().getStatus(list.get(i).getStatus());
    viewHolder.name1.setText(name);
    viewHolder.date1.setText(date);
    viewHolder.status.setText(stat);
}

    }catch (NullPointerException e){

    }catch (IndexOutOfBoundsException ignore){

    } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name1,date1,status;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name1=itemView.findViewById(R.id.partner_name);
            date1=itemView.findViewById(R.id.tv_date);
            cardView=itemView.findViewById(R.id.servicerequestcv);
            status=itemView.findViewById(R.id.paymentstatus);

            cardView.setOnClickListener(this);
        /**   itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Gson gson=new Gson();
                   int position=getAdapterPosition();
                   String data=gson.toJson(list.get(position));
                   Intent i=new Intent(context, ServiceRequestDetail.class);
                   i.putExtra("data",data);
                  context.startActivity(i);
               }
           });**/
        }

        @Override
        public void onClick(View v) {
            Gson gson=new Gson();
            int position=getAdapterPosition();
            String data=gson.toJson(list.get(position));
            Intent i=new Intent(context, ServiceRequestDetail.class);
            i.putExtra("data",data);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
