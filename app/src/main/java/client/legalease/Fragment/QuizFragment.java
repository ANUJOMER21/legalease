package client.legalease.Fragment;

import android.os.Bundle;
;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.AdapterQuiz;
import client.legalease.Model.QuizModel.QuestionSet.Quizset;
import client.legalease.Model.QuizModel.QuestionSet.SetModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizFragment extends Fragment {
    ProgressBar progressBar;
    RecyclerView rv_quiz;

    List<Quizset> quizsets;
    AdapterQuiz adapterQuiz;

    CommonSharedPreference commonSharedPreference;
    String token = "";
    int id = 0;
    String mobile = "";
    String name = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        rv_quiz = (RecyclerView)rootView.findViewById(R.id.rv_quiz);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);


        commonSharedPreference = new CommonSharedPreference(getActivity());



        try {

            if (commonSharedPreference.loggedin()){
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getActivity()).getId();
                mobile = commonSharedPreference.getLoginSharedPref(getActivity()).getMobile();
//                name = commonSharedPreference.getLoginSharedPref(getActivity()).getName();

            }else {
                token = "";
                id = 0;
                mobile = "";
                name= "User Name";

            }

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
        quizsets = new ArrayList<>();
        getMyQuiz();
        return rootView;
    }





    private void getMyQuiz() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<SetModel> call = api.getQuestionSet(token);
        call.enqueue(new Callback<SetModel>() {
            @Override
            public void onResponse(Call<SetModel> call, Response<SetModel> response) {


                progressBar.setVisibility(View.GONE);

                try {
                    quizsets = response.body().getQuizset();


                    adapterQuiz = new AdapterQuiz(getContext(), quizsets);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rv_quiz.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                    rv_quiz.setAdapter(adapterQuiz);
                } catch (NullPointerException ignored) {

                } catch (Exception ignore) {

                }
            }

            @Override
            public void onFailure(Call<SetModel> call, Throwable t) {
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }
        });

    }

}

