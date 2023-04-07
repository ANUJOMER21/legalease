package client.legalease.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.VideoCategoryAdapter;
import client.legalease.Model.VideoCategoryModel.CategoryData;
import client.legalease.Model.VideoCategoryModel.VideoCategory;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentVideo extends Fragment {

    RecyclerView rv_video;
    ProgressBar progressBar;
    List<CategoryData> videoCategoryData;
    VideoCategoryAdapter videoCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        videoCategoryData =new ArrayList<>();
        initializeViews(rootView);
        getData();

        return rootView;
    }

    private void getData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<VideoCategory> call = api.getVideoCategory();
        call.enqueue(new Callback<VideoCategory>() {
            @Override
            public void onResponse(Call<VideoCategory> call, Response<VideoCategory> response) {
                progressBar .setVisibility(View.GONE);
                try {
                    videoCategoryData = response.body().getData();
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                videoCategoryAdapter = new VideoCategoryAdapter(getContext(),videoCategoryData);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                rv_video.setLayoutManager(layoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_video.setAdapter(videoCategoryAdapter);

            }

            @Override
            public void onFailure(Call<VideoCategory> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void initializeViews(View rootView) {
        rv_video =(RecyclerView)rootView.findViewById(R.id.rv_video);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
    }




}

