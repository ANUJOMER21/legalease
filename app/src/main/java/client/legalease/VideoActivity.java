package client.legalease;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Adapter.VideoAdapter;
import client.legalease.Model.VideoModel.VideoData;
import client.legalease.Model.VideoModel.VideoModel;
import client.legalease.Utilities.PaginationScrollListener;
import client.legalease.WebServices.ApiService;
import client.legalease.api.MyPostApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity {



    RecyclerView rv_video;
    ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 6;
    private int currentPage = PAGE_START;
    VideoAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<VideoData> results;
    ApiService apiService;
    String categoryId = "";
    String videoTitle ="";
    ImageView back;
    TextView tv_categoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initializeViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      try {
          Intent intent = getIntent();
          categoryId  =intent.getStringExtra("categoryId");
          videoTitle = intent.getStringExtra("videoTitle");
      }catch (NullPointerException ignored){

      }

        tv_categoryName.setText(videoTitle);


        getData();
    }



    private void initializeViews() {
        rv_video =(RecyclerView)findViewById(R.id.rv_video);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        back = (ImageView)findViewById(R.id.back);
        tv_categoryName =(TextView)findViewById(R.id.tv_categoryName);
    }





    private void getData() {
        adapter = new VideoAdapter(VideoActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        rv_video.setLayoutManager(layoutManager);
        rv_video.setItemAnimator(new DefaultItemAnimator());
        rv_video.setAdapter(adapter);
        rv_video.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override

            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        //init service and load data
        apiService = MyPostApi.getClient().create(ApiService.class);
        loadFirstPage();
    }

    private void loadFirstPage() {
        call().enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                // Got data. Send it to adapter
                progressBar.setVisibility(View.GONE);


                try {
                    results = fetchResults(response);

                    if (results.size()==0){
                        Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();

                    }
                    else if (results.size()!=0) {
//                adapter.addAll(results);
                        try {
                            adapter.addAll(results);
                        }catch (NullPointerException ignored){
                            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
                        }
                        rv_video.setVisibility(View.VISIBLE);
                    }
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private List<VideoData> fetchResults(Response<VideoModel> response) {
        VideoModel myPost = null;
        try {
            myPost = response.body();
        }catch (NullPointerException ignored){

        }
        return myPost.getData();
    }

    private void loadNextPage() {
        progressBar.setVisibility(View.VISIBLE);

        call().enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                adapter.removeLoadingFooter();
                isLoading = false;
                try {
                    results = fetchResults(response);
                }catch (NullPointerException ignored){
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                try {
                    if ((results.isEmpty())||(results.equals(null))) {
//                        Toasty.info(getActivity(), "No More Data", 2000).show();
                    } else {
                        adapter.addAll(results);

                    }
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }

//                adapter.addAll(results);

                if (currentPage != TOTAL_PAGES) {
                    adapter.addLoadingFooter();
                } else {
                    isLastPage = true;
//                    Toasty.info(getApplicationContext(), "No More Data", 2000).show();

                }

            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }

    private Call<VideoModel> call() {
        return apiService.getVideoData(categoryId,currentPage);
    }

}
