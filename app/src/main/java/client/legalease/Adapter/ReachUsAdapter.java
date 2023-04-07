package client.legalease.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import client.legalease.Model.ReachUsModel.ReachUsData;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ReachUsAdapter extends RecyclerView.Adapter<ReachUsAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<ReachUsData> reachUsData;

    private Context context;
    DownloadManager downoadManager;

    public ReachUsAdapter(Context context, List<ReachUsData> reachUsData) {
        this.context = context;
        this.reachUsData = reachUsData;
    }



    @NonNull
    @Override
    public ReachUsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reachus, viewGroup, false);


        return new ReachUsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReachUsAdapter.ViewHolder viewHolder, int i) {

        String image="";
        String name = "";
        String address ="";
        String companyName = "";
        String mobile = "";




        try {
            image =IMAGEURL+reachUsData.get(i).getPhoto();
            name = reachUsData.get(i).getName();
            address =reachUsData.get(i).getAddress();
            companyName = reachUsData.get(i).getCompanyName();
            mobile  =reachUsData.get(i).getMobile();

            viewHolder.tv_name.setText(name);
            viewHolder.tv_address.setText(address);
            viewHolder.tv_companyName.setText(companyName);
            viewHolder.tv_mobile.setText(mobile);

            Glide.with(context)
                    .load(image)
                    .apply(fitCenterTransform())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            viewHolder.image_progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            viewHolder.image_progress.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(viewHolder.clientImage);

        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return reachUsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_companyName,tv_address,tv_mobile;
        ImageView clientImage;
        ProgressBar image_progress;
        CardView card_reachHim;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_companyName = (TextView) itemView.findViewById(R.id.tv_companyName);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            clientImage =(ImageView)itemView.findViewById(R.id.clientImage);
            image_progress =(ProgressBar)itemView.findViewById(R.id.image_progress);
            card_reachHim = (CardView)itemView.findViewById(R.id.card_reachHim);
            card_reachHim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =  getAdapterPosition();
                    ReachUsData myData = reachUsData.get(pos);
                    String lat = "";
                    String lng = "";

                    try {
                        lat = myData.getLatitude();
                        lng =myData.getLongitude();
                        String strUri = "http://maps.google.com/maps?q=loc:" +lat + "," + lng;
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        context.startActivity(intent);

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                }
            });


        }
    }

}
