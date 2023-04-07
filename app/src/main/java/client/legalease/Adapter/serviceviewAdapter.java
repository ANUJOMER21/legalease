package client.legalease.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import client.legalease.Model.ServiceOfferModel.OrderService;
import client.legalease.R;

public class serviceviewAdapter extends RecyclerView.Adapter<serviceviewAdapter.Viewholder> {
  List<OrderService> orderServiceList;
  Context context;

    public serviceviewAdapter(List<OrderService> orderServiceList, Context context) {
        this.orderServiceList = orderServiceList;
        this.context = context;
    }

    @NonNull
    @Override
    public serviceviewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.serviceview,viewGroup,false);
      return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull serviceviewAdapter.Viewholder viewholder, int i) {
          String name="";
          int amounnt=0;
          if(orderServiceList.get(i)!=null){
              name=orderServiceList.get(i).getTitle();
              amounnt=orderServiceList.get(i).getAmount();
          }
          viewholder.textView.setText(name);
          viewholder.rate.setText("₹ "+amounnt);
    }

    @Override
    public int getItemCount() {
        return orderServiceList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView textView,rate;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
 rate=itemView.findViewById(R.id.rate);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
