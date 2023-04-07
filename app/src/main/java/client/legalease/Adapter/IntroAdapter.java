package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.legalease.Model.Appslider.SliderData;
import client.legalease.Model.Services.ServicesParentModule.Service;
import client.legalease.R;
import client.legalease.SubServicesActivity;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    List<SliderData> sliderData;
    private Context context;

    public IntroAdapter(Context context, List<SliderData> sliderData) {
        this.context = context;
        this.sliderData = sliderData;
    }



    @NonNull
    @Override
    public IntroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_intro, viewGroup, false);


        return new IntroAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IntroAdapter.ViewHolder viewHolder, int i) {


        String imageUrl = "";
        String finalImage = "";


        try {

            imageUrl = sliderData.get(i).getImage();





        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }

        finalImage =IMAGEURL+imageUrl;

        if (imageUrl.equals("")||imageUrl.equals(null)){
            Glide.with(context)
                    .load(R.drawable.service)
                    .apply(fitCenterTransform())
                    .into(viewHolder.image_intro);

        }else
            Glide.with(context)
                    .load(finalImage)
                    .apply(fitCenterTransform())
                    .into(viewHolder.image_intro);



    }

    @Override
    public int getItemCount() {
        return sliderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView service_text;

        ImageView image_intro;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            image_intro = (ImageView) itemView.findViewById(R.id.image_intro);

        }
    }

}
