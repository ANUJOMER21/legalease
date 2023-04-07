package client.legalease.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Model.Services.ServiceDetails.Deliverable;
import client.legalease.R;

public class AdapterDelever extends RecyclerView.Adapter<AdapterDelever.ViewHolder> {


    public static List<Deliverable> deliverableList;

    private Context context;

    public AdapterDelever(Context context, List<Deliverable> deliverableList) {
        this.context = context;
        this.deliverableList = deliverableList;
    }



    @NonNull
    @Override
    public AdapterDelever.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_requisties, viewGroup, false);


        return new AdapterDelever.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDelever.ViewHolder viewHolder, int i) {

        String title_value="";



        try {
            title_value = deliverableList.get(i).getTitle();


            viewHolder.title.setText(title_value);



        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return deliverableList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}
