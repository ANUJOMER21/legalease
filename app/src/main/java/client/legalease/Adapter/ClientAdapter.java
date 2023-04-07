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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import client.legalease.AccountantDetails;
import client.legalease.MakeNoteActivity;
import client.legalease.Model.AccountantModel.AccountantData;
import client.legalease.MyAccountantListActivity;
import client.legalease.R;


import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {


    List<AccountantData> clientData;
    private MyAccountantListActivity context;

    public ClientAdapter(MyAccountantListActivity context, List<AccountantData> clientData) {
        this.context = context;
        this.clientData = clientData;
    }



    @NonNull
    @Override
    public ClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_accountant, viewGroup, false);


        return new ClientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClientAdapter.ViewHolder viewHolder, int i) {

        String name ="";
        String email ="";
        String mobile ="";
        String imageUrl = "";


        try {
            name = clientData.get(i).getName();
            email = clientData.get(i).getEmail();
            mobile = clientData.get(i).getMobile();
            imageUrl = IMAGEURL+clientData.get(i).getPhoto();

            viewHolder.tv_name.setText(name);
            viewHolder.tv_email.setText(email);
            viewHolder.tv_mobile.setText(mobile);


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


        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return clientData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_email,tv_mobile;
        ProgressBar image_progress;
        ImageView clientImage;
        ImageView iv_makeNote;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            image_progress = (ProgressBar)itemView.findViewById(R.id.image_progress);
            clientImage = (ImageView)itemView.findViewById(R.id.clientImage);
            iv_makeNote = (ImageView)itemView.findViewById(R.id.iv_makeNote);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String accountantId = "";
                    String name = "";
                    int pos = getAdapterPosition();
                    AccountantData mydata = null;

                    try {
                        mydata = clientData.get(pos);
                        accountantId = mydata.getId();
                        name = mydata.getName();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }

                    Intent intent  =new Intent(context, AccountantDetails.class);
                    intent.putExtra("accountantId",accountantId);
                    intent.putExtra("name",name);

                    context.startActivity(intent);
                }
            });


            iv_makeNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String accountantId = "";
                    String name = "";
                    int pos = getAdapterPosition();
                    AccountantData mydata = null;

                    try {
                        mydata = clientData.get(pos);
                        accountantId = mydata.getId();
                        name = mydata.getName();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Intent intent  =new Intent(context, MakeNoteActivity.class);
                    intent.putExtra("accountantId",accountantId);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                }
            });
        }
    }

}
