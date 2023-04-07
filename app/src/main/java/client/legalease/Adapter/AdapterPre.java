package client.legalease.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Model.Services.ServiceDetails.Requisty;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

public class AdapterPre extends RecyclerView.Adapter<AdapterPre.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<Requisty> preRequestList;

    private Context context;

    public AdapterPre(Context context, List<Requisty> preRequestList) {
        this.context = context;
        this.preRequestList = preRequestList;
    }



    @NonNull
    @Override
    public AdapterPre.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_requisties, viewGroup, false);


        return new AdapterPre.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterPre.ViewHolder viewHolder, int i) {

        String title_value="";
        String  content_value="";



        try {
            title_value = preRequestList.get(i).getTitle();


            viewHolder.title.setText(title_value);



        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return preRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

}
