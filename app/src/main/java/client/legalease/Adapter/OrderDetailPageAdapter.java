package client.legalease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Model.Acceptedordermodel.OrderService;
import client.legalease.R;

public class OrderDetailPageAdapter extends RecyclerView.Adapter<OrderDetailPageAdapter.ViewHOder> {
    List<OrderService> list;
    Context context;

    public OrderDetailPageAdapter(List<OrderService> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderDetailPageAdapter.ViewHOder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.serviceview,parent,false);
        return new OrderDetailPageAdapter.ViewHOder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailPageAdapter.ViewHOder holder, int position) {
        String name="";
        String amounnt="";
        if(list.get(position)!=null){
            name=list.get(position).getTitle();
            amounnt=String.valueOf(list.get(position).getAmount());
        }
        holder.textView.setText(name);
        holder.rate.setText("₹ "+amounnt);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHOder extends RecyclerView.ViewHolder {
        TextView textView,rate;
        public ViewHOder(@NonNull View itemView) {
            super(itemView);
            rate=itemView.findViewById(R.id.rate);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
