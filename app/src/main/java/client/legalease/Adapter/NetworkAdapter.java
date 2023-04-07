package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import client.legalease.AccountantDetails;
import client.legalease.MakeNoteActivity;
import client.legalease.Model.AccountantModel.AccountantData;
import client.legalease.Model.MyNetworkModel.NetworkUser;
import client.legalease.MyAccountantListActivity;
import client.legalease.MyNetworkDetails;
import client.legalease.R;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.ViewHolder> {


    public static List<NetworkUser> networkUsers;
    private Context context;

    public NetworkAdapter(Context context, List<NetworkUser> networkUsers) {
        this.context = context;
        this.networkUsers = networkUsers;
    }



    @NonNull
    @Override
    public NetworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mynetwork, viewGroup, false);


        return new NetworkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NetworkAdapter.ViewHolder viewHolder, int i) {

        String name ="";
        String revenue ="";
        String earning ="";
        String imageUrl = "";

        try {
            name = networkUsers.get(i).getName();


            viewHolder.tv_name.setText(name);

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }

        try {


            imageUrl = IMAGEURL+networkUsers.get(i).getPhoto();

            if (networkUsers.get(i).getPhoto().equals(null)||networkUsers.get(i).getPhoto().equals("")){
                viewHolder.image_progress.setVisibility(View.GONE);
                Glide.with(context).load(R.drawable.boy).into(viewHolder.clientImage);

            }else {
                Glide.with(context)
                        .load(imageUrl)
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

            }

        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }
        float x;
        float y;

        try {


            y= Float.parseFloat(networkUsers.get(i).getEarning());
            earning =  String.valueOf(Math.round(y));

            if (earning.equals(null)||earning.equals("")){
                viewHolder.tv_mobile.setText("00/-");

            }else {

                viewHolder.tv_mobile.setText(earning+"/-");

            }

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
        try {
            x= Float.parseFloat(networkUsers.get(i).getRevenue());
            revenue = String.valueOf(Math.round(x));
            if (revenue.equals(null)||revenue.equals("")){
                viewHolder.tv_email.setText("00/-");

            }else {

                viewHolder.tv_email.setText(revenue+"/-");

            }
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return networkUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_email, tv_mobile;
        ProgressBar image_progress;
        ImageView clientImage;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            image_progress = (ProgressBar) itemView.findViewById(R.id.image_progress);
            clientImage = (ImageView) itemView.findViewById(R.id.clientImage);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =getAdapterPosition();
                    NetworkUser myNetworkUser = networkUsers.get(pos);
                    String id = String.valueOf(myNetworkUser.getId());
                    String name = myNetworkUser.getName();
                    Intent intent = new Intent(context, MyNetworkDetails.class);
                    intent.putExtra("userId",id);
                    intent.putExtra("name",name);
                    Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });

        }
    }
}
