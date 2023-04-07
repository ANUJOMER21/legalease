package client.legalease.Adapter;

import android.content.Intent;
import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Model.VideoModel.VideoData;
import client.legalease.R;
import client.legalease.SingleVideoActivity;
import client.legalease.VideoActivity;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<VideoData> myPostResult = null;

    ApiService apiService = ApiClient.getClient().create(ApiService.class);
    private boolean isLoadingAdded = false;


    VideoActivity context;

    String videoUrl = "";
    SimpleExoPlayer player;
    String title = "";







    public VideoAdapter(VideoActivity context) {
        this.context = context;
        myPostResult = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new VideoAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.video_item, parent, false);
        viewHolder = new VideoAdapter.PostViewHolder(v1);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        VideoData result = null;

        try {
            result = myPostResult.get(position);


        }catch (NullPointerException ignored){

        }catch (ArrayIndexOutOfBoundsException ignore){

        }catch (IndexOutOfBoundsException ignor){

        }

        switch (getItemViewType(position)) {
            case ITEM:
                final VideoAdapter.PostViewHolder postViewHolder = (VideoAdapter.PostViewHolder) holder;

                try {

                    videoUrl = IMAGEURL+result.getTutoral();
                    title = result.getTitle();
                    postViewHolder.tv.setText(title);


                    Glide.with(context)
                            .load(videoUrl)
                            .apply(fitCenterTransform())
                            .into(postViewHolder.imageView);
                    //                    String url = "http://capanel.in/public/uploads/2019-06/follow-legalease.mp4";
//
//
//                    MediaController m = new MediaController(context);
//                    postViewHolder.video_view.setMediaController(m);
//
//
//                    Uri u = Uri.parse(url);
//
//                    postViewHolder.video_view.setVideoURI(u);
//
//                    postViewHolder.video_view.start();


                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException igbnore){

                }


                break;

            case LOADING:
//                Do nothing
                break;
        }


    }



    @Override
    public int getItemCount() {
        return myPostResult == null ? 0 : myPostResult.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == myPostResult.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(VideoData r) {
        myPostResult.add(r);
        notifyItemInserted(myPostResult.size() - 1);
    }

    public void addAll(List<VideoData> videoResults) {
        for (VideoData result : videoResults) {
            add(result);
        }
    }

    public void remove(VideoData r) {
        int position = myPostResult.indexOf(r);
        if (position > -1) {
            myPostResult.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new VideoData());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = myPostResult.size() - 1;
        VideoData result = getItem(position);

        if (result != null) {
            myPostResult.remove(position);
            notifyItemRemoved(position);
        }
    }

    public VideoData getItem(int position) {

        VideoData myData = null;

        try{
            myData =  myPostResult.get(position);
        }catch (ArrayIndexOutOfBoundsException ignored){

        }catch (IndexOutOfBoundsException ignore)
        {

        }catch (NullPointerException ignor){

        }
        return myData;
    }




   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */

    protected class PostViewHolder extends RecyclerView.ViewHolder {
        VideoView video_view;
        PlayerView player_view;
        ImageView imageView;
        TextView tv;
        ImageView image_play;



        public PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            video_view = (VideoView) itemView.findViewById(R.id.video_view);
            player_view = (PlayerView)itemView.findViewById(R.id.player_view);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            tv=(TextView)itemView.findViewById(R.id.tv);
            image_play = (ImageView)itemView.findViewById(R.id.iv_playVideo);


            image_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    VideoData mydata = myPostResult.get(pos);
                    Log.d("hgsdgsd", String.valueOf(mydata));
                    String videoUrl =IMAGEURL+ mydata.getTutoral();
                    Intent intent = new Intent(context, SingleVideoActivity.class);
                    intent.putExtra("videoUrl",videoUrl);
                    context.startActivity(intent);


                }
            });


        }






    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
