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

import client.legalease.Model.VideoCategoryModel.CategoryData;
import client.legalease.R;
import client.legalease.VideoActivity;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoCategoryAdapter extends RecyclerView.Adapter<VideoCategoryAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    List<CategoryData> videoCategoryData;
    private Context context;


    public VideoCategoryAdapter(Context context, List<CategoryData> videoCategoryData) {
        this.context = context;
        this.videoCategoryData = videoCategoryData;
    }


    @NonNull
    @Override
    public VideoCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_category, viewGroup, false);


        return new VideoCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoCategoryAdapter.ViewHolder viewHolder, int i) {

        String name ="";

        String imageUrl = "";
        String finalImage = "";


        try {
            name = videoCategoryData.get(i).getCategory();

            imageUrl = videoCategoryData.get(i).getImage();

            viewHolder.category_name.setText(name);




        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }

        finalImage =IMAGEURL+imageUrl;

        if (imageUrl.equals("")||imageUrl.equals(null)){
            Glide.with(context)
                    .load(R.drawable.service)
                    .apply(fitCenterTransform())
                    .into(viewHolder.category_Image);

        }else
            Glide.with(context)
                    .load(finalImage)
                    .apply(fitCenterTransform())
                    .into(viewHolder.category_Image);


    }

    @Override
    public int getItemCount() {
        return videoCategoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;

        ImageView category_Image;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            category_Image = (ImageView) itemView.findViewById(R.id.category_Image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryId = "";
                    String videoTitle = "";
                    int pos = getAdapterPosition();
                    CategoryData mydata = null;

                    try {
                        mydata = videoCategoryData.get(pos);
                        categoryId = String.valueOf(mydata.getId());
                        videoTitle = mydata.getCategory();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Intent intent  =new Intent(context, VideoActivity.class);
                    intent.putExtra("categoryId",categoryId);
                    intent.putExtra("videoTitle",videoTitle);
                    context.startActivity(intent);
                }
            });
        }
    }

}
