package client.legalease.Adapter;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import client.legalease.Model.Uploadeddocmodel;
import client.legalease.R;

public class UploadedDocumentAdapter2 extends RecyclerView.Adapter<ViewHolder> {

    ArrayList<Uploadeddocmodel> uploadeddocmodelArrayList;
    Context context;

    public UploadedDocumentAdapter2(ArrayList<Uploadeddocmodel> uploadeddocmodelArrayList, Context context) {
        this.uploadeddocmodelArrayList = uploadeddocmodelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_getuploaded_doc, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String docName="";
        String  docImage="";
        try {
            docName = uploadeddocmodelArrayList.get(position).getNameofdoc();
            docImage = IMAGEURL+uploadeddocmodelArrayList.get(position).getImageofdoc();


            viewHolder.tv_doc.setText(docName);


            Glide.with(context)
                    .load(docImage)
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
                    .into(viewHolder.iv_docImage);




        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return uploadeddocmodelArrayList.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder {
    TextView tv_doc;
    ImageView iv_docImage;
    ProgressBar image_progress;


    public ViewHolder(@NonNull final View itemView) {
        super(itemView);
        tv_doc = (TextView) itemView.findViewById(R.id.tv_doc);
        iv_docImage = (ImageView) itemView.findViewById(R.id.iv_docImage);
        image_progress=(ProgressBar) itemView.findViewById(R.id.image_progress);
    }
}
