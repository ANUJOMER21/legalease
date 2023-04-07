package client.legalease.Adapter;

import android.content.Context;
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

import client.legalease.Model.UploadedDocumentModel.UploadedData;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterUploadedDocument extends RecyclerView.Adapter<AdapterUploadedDocument.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<UploadedData> uploadedDataList;

    private Context context;

    public AdapterUploadedDocument(Context context, List<UploadedData> uploadedDataList) {
        this.context = context;
        this.uploadedDataList = uploadedDataList;
    }



    @NonNull
    @Override
    public AdapterUploadedDocument.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_getuploaded_doc, viewGroup, false);


        return new AdapterUploadedDocument.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterUploadedDocument.ViewHolder viewHolder, int i) {

        String docName="";
        String  docImage="";



        try {
            docName = uploadedDataList.get(i).getFiletypelist().getRequireDoc();
            docImage = IMAGEURL+uploadedDataList.get(i).getUploadedDoc();


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
        return uploadedDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
}
