package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import client.legalease.BillListActivity;
import client.legalease.BillUploadActivty;
import client.legalease.Model.MyDocuments.DocumentData;
import client.legalease.R;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterDocument extends RecyclerView.Adapter<AdapterDocument.ViewHolder> {


    public static List<DocumentData> documentDataList;

    private BillUploadActivty context;

    public AdapterDocument(BillUploadActivty context, List<DocumentData> documentDataList) {
        this.context = context;
        this.documentDataList = documentDataList;
    }



    @NonNull
    @Override
    public AdapterDocument.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_document, viewGroup, false);


        return new AdapterDocument.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDocument.ViewHolder viewHolder, int i) {

        String title="";
        String  date="";
        String noOfDocs = "";
        String imageUrl = "";
        String urlToBeCheck = "";




        try {
            title = documentDataList.get(i).getDocumenttype();
            date = documentDataList.get(i).getCreatedAt();
            noOfDocs = String.valueOf(documentDataList.get(i).getDoccount()) + " Page";
            imageUrl = IMAGEURL+documentDataList.get(i).getPhoto();

            viewHolder.doc_title.setText(title);
            viewHolder.doc_date.setText(date);
            viewHolder.doc_noOfFiles.setText(noOfDocs);

            urlToBeCheck = documentDataList.get(i).getPhoto().substring(documentDataList.get(i).getPhoto().length()-4);
            if (urlToBeCheck.equals(".pdf")){
                viewHolder.relative_image.setVisibility(View.GONE);
                viewHolder.relative_pdf.setVisibility(View.VISIBLE);
                FileLoader.with(context)
                        .load(imageUrl)
                        .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                        .asFile(new FileRequestListener<File>() {
                            @Override
                            public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                File file = response.getBody();

                                viewHolder.pdfView.fromFile(file)
                                        .password(null)
                                        .defaultPage(0)
                                        .enableSwipe(true)
                                        .load();
                                // do something with the file
                            }

                            @Override
                            public void onError(FileLoadRequest request, Throwable t) {
                            }
                        });


            }else {
                viewHolder.relative_image.setVisibility(View.VISIBLE);
                viewHolder.relative_pdf.setVisibility(View.GONE);


                Glide.with(context)
                        .load(imageUrl)
                        .apply(fitCenterTransform())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                viewHolder.progress_image.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                viewHolder.progress_image.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(viewHolder.image);

            }
        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return documentDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView doc_title,doc_date,doc_noOfFiles;
        ImageView image;
        ProgressBar progress_image;
        RelativeLayout  relative_image,relative_pdf;
        PDFView pdfView;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            doc_title = (TextView) itemView.findViewById(R.id.doc_title);
            doc_date = (TextView) itemView.findViewById(R.id.doc_date);
            doc_noOfFiles = (TextView) itemView.findViewById(R.id.doc_noOfFiles);
            image = (ImageView)itemView.findViewById(R.id.image);
            progress_image = (ProgressBar)itemView.findViewById(R.id.progress_image);
            relative_image  =(RelativeLayout)itemView.findViewById(R.id.relative_image);
            relative_pdf  =(RelativeLayout)itemView.findViewById(R.id.relative_pdf);
            pdfView = (PDFView)itemView.findViewById(R.id.pdfView);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    DocumentData myData = new DocumentData();
                    int imageList = 0;
                    String urlToBeCheck = "";
                    String url = "";
                    String[] imageValue=null ;

                    try {
                        myData = documentDataList.get(pos);
                        url = IMAGEURL+myData.getPhoto();
                        urlToBeCheck = myData.getPhoto().substring(myData.getPhoto().length()-4);


                        imageList = myData.getDoccount();
                        if (imageList>1){
                           try {
                               imageValue = new String[imageList];

                               for (int i=0;i<imageList;i++){
                                   String imageUrl = IMAGEURL+myData.getAlldoc().get(i).getPhoto();
                                   imageValue[i]=imageUrl;

                               }
                           }catch (NullPointerException ignored){

                           }catch (ArrayIndexOutOfBoundsException ignore){

                           }catch (IndexOutOfBoundsException ignor){

                           }


                                Intent intent = new Intent(context, BillListActivity.class);
                                intent.putExtra("imageValue",imageValue);
                                intent.putExtra("urlToBeCheck",urlToBeCheck);
                                intent.putExtra("imageList", String.valueOf(imageList));
                                context.startActivity(intent);


                        }else {
                            Intent intent = new Intent(context, BillListActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("urlToBeCheck", urlToBeCheck);
                            intent.putExtra("imageList", String.valueOf(imageList));

                            context.startActivity(intent);
                        }

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignre){

                    }



                }
            });
        }
    }
}

