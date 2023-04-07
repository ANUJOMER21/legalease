package client.legalease.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Model.Services.ServiceDetails.Faq;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

public class AdapterFaq extends RecyclerView.Adapter<AdapterFaq.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<Faq> faqList;

    private Context context;

    public AdapterFaq(Context context, List<Faq> faqList) {
        this.context = context;
        this.faqList = faqList;
    }



    @NonNull
    @Override
    public AdapterFaq.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_benefit, viewGroup, false);


        return new AdapterFaq.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFaq.ViewHolder viewHolder, int i) {

        String title_value="";
        String  content_value="";



        try {
            title_value = faqList.get(i).getTitle();
            content_value = faqList.get(i).getDescription();


            viewHolder.title.setText(title_value);
            viewHolder.content.setText(content_value);
            viewHolder.contentm.setText(content_value);


        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,content,contentm;
        ProgressBar image_progress;
        ImageView image_expand,image_hide;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            image_expand = (ImageView)itemView.findViewById(R.id.image_expand);
            image_hide = (ImageView)itemView.findViewById(R.id.image_hide);
            contentm  =(TextView)itemView.findViewById(R.id.contentm);

            image_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content.setVisibility(View.GONE);
                    contentm.setVisibility(View.VISIBLE);
                    image_expand.setVisibility(View.GONE);
                    image_hide.setVisibility(View.VISIBLE);
                }
            });

            image_hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content.setVisibility(View.VISIBLE);
                    contentm.setVisibility(View.GONE);
                    image_expand.setVisibility(View.VISIBLE);
                    image_hide.setVisibility(View.GONE);
                }
            });


        }
    }

}
