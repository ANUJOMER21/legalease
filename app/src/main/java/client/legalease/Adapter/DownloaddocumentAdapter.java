package client.legalease.Adapter;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import client.legalease.Download_Uploaded_Document;
import client.legalease.Model.Downloaddocumentmodel;
import client.legalease.R;

public class DownloaddocumentAdapter extends RecyclerView.Adapter<View6> {
    URL url;
    ProgressDialog mProgressDialog;
    ArrayList<Downloaddocumentmodel> downloaddocumentmodelArrayList;
    Context context;
    AsyncTask mMyTask;
    DownloadManager manager;

    public DownloaddocumentAdapter(ArrayList<Downloaddocumentmodel> downloaddocumentmodelArrayList, Context context) {
        this.downloaddocumentmodelArrayList = downloaddocumentmodelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View6 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viee = LayoutInflater.from(parent.getContext()).inflate(R.layout.downloaduploadeddocument, parent, false);
        return new View6(viee);
    }

    @Override
    public void onBindViewHolder(@NonNull View6 holder, int position) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("AsyncTask");
        mProgressDialog.setMessage("Please wait, we are downloading your image file...");
        String docName = "";
        String docImage = "";
        String docpage = "";
        try {
            docName = downloaddocumentmodelArrayList.get(position).getDocname();
            docImage = IMAGEURL + downloaddocumentmodelArrayList.get(position).getDocimage();
            docpage = downloaddocumentmodelArrayList.get(position).getDocpage();
            holder.tv_doc_name.setText(docName);
           /** if (docpage.equals("1")) {
                holder.page.setText("Front Page");
            } else if (docpage.equals("2")) {
                holder.page.setText("Back Page");

            } else {
                holder.page.setText("Other Page");
            }**/
            Glide.with(context).
                    load(docImage).
                    apply(fitCenterTransform())
                    .into(holder.iv_docImage);

            String finalDocImage = docImage;

            String finalDocName = docName;
            holder.page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /** Glide.with(context)
                         .asBitmap()
                         .load(finalDocImage)
                         .into(new SimpleTarget<Bitmap>(100,100) {
                             @Override
                             public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                 saveImage(resource);
                             }
                         });**/
               downloadImageNew(finalDocName,finalDocImage);
                }
            });
        } catch (NullPointerException e) {

        } catch (IndexOutOfBoundsException ignore) {

        }

    }
    private void downloadImageNew(String filename, String downloadUrlOfImage){
        try{
            Log.d("downloadimage", "downloadImageNew: working");
            DownloadManager dm = (DownloadManager)context. getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(downloadUrlOfImage);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(context, "Image download started.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Image download failed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public int getItemCount() {
        return downloaddocumentmodelArrayList.size();
    }

    private String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = "JPEG_" + "FILE_NAME" + ".jpg";
        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/YOUR_FOLDER_NAME");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

}
class View6 extends RecyclerView.ViewHolder {
    TextView tv_doc_name, page;
    ImageView iv_docImage;
    ProgressBar image_progress;
    CardView cardView;


    public View6(@NonNull final View itemView) {
        super(itemView);
        tv_doc_name = (TextView) itemView.findViewById(R.id.document_name);
        iv_docImage = (ImageView) itemView.findViewById(R.id.frontpageimg);
        //image_progress=(ProgressBar) itemView.findViewById(R.id.progresbar);
        page = (TextView) itemView.findViewById(R.id.page);
        cardView = itemView.findViewById(R.id.frontpageimgcv);

    }


}

