package client.legalease;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Model.QuizModel.QuizFinalResult.Data;
import client.legalease.Model.QuizModel.QuizFinalResult.QuizResult;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalQuizResult extends AppCompatActivity {
    String token ="";
    String setId = "";
    int id =0;
    List<Data> quizData;
    QuizResultAdapter quizResultAdapter;
    RecyclerView rv_quizResult;
    ProgressBar progressBar;
    String name ="";
    TextView totalMarks;
    TextView userName;
    int finalMarks =0;
    Button btn_close;


    CommonSharedPreference commonSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_quiz_result);
        commonSharedPreference  = new CommonSharedPreference(getApplicationContext());
        quizData = new ArrayList<>();
        initializeViews();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(FinalQuizResult.this,HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();
//                name =commonSharedPreference.getLoginSharedPref(getApplicationContext()).getName().toUpperCase();
//                String message ="Hi "+ name + ", thanks for your participation in this quiz. Following is your response and totals marks.";
//                userName.setText(message);
            }
        }catch (NullPointerException ignored){

        }


        try {
            Intent intent = getIntent();
            setId = intent.getStringExtra("setId");
        }catch (NullPointerException ignored){

        }

        getMyQuizResult();

    }

    private void initializeViews() {
        rv_quizResult = (RecyclerView)findViewById(R.id.rv_quizResult);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        userName  = (TextView)findViewById(R.id.userName);
        totalMarks  =(TextView)findViewById(R.id.totalMarks);
        btn_close  =(Button) findViewById(R.id.btn_close);
    }

    private void getMyQuizResult() {
              ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<QuizResult> call = api.getMyResult(token,setId);
            call.enqueue(new Callback<QuizResult>() {
                @Override
                public void onResponse(Call<QuizResult> call, Response<QuizResult> response) {


                    progressBar.setVisibility(View.GONE);
                    try {
                        quizData = response.body().getData();

                    }catch (NullPointerException ignored){

                    }


                    quizResultAdapter = new QuizResultAdapter(getApplicationContext(),quizData);

                    LinearLayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                    rv_quizResult.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                    rv_quizResult.setAdapter(quizResultAdapter);

                }

                @Override
                public void onFailure(Call<QuizResult> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });

        }



    public class QuizResultAdapter  extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        List<Data> quizData;
        private Context context;

        public QuizResultAdapter(Context context, List<Data> quizData) {
            this.context = context;
            this.quizData = quizData;
        }



        @NonNull
        @Override
        public QuizResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quiz, viewGroup, false);


            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull QuizResultAdapter.ViewHolder viewHolder, int i) {

            String question ="";

            String correctanswer = "";
            String givenansweer = "";
            String answerstatus = "";
            String myMarks = "";
            String cQuestion = "";


            try {
                question = quizData.get(i).getQuestion();
                correctanswer =quizData.get(i).getCorrectAnswer();
                givenansweer =quizData.get(i).getGivenansweer();
                answerstatus =quizData.get(i).getAnswerstatus();
                cQuestion = String.valueOf(i+1);
                if (answerstatus.equals("notcorrect")){
                    myMarks="0";
                    viewHolder.tv_correctIncorrect.setBackgroundResource(R.color.red_incorrect);
                    finalMarks=finalMarks;
                }else {
                    myMarks = "1";
                    viewHolder.tv_correctIncorrect.setBackgroundResource(R.color.blue);
                    finalMarks++;


                }

                viewHolder.question_number.setText(cQuestion +" . ");
                viewHolder.tv_question.setText(question);
                viewHolder.tv_correctAnswer.setText(correctanswer);
                viewHolder.tv_myAnswer.setText(givenansweer);
                viewHolder.tv_correctIncorrect.setText(answerstatus);
                viewHolder.tv_marks.setText(myMarks);

                String myFinalMarks = String.valueOf(finalMarks);
                totalMarks.setText(myFinalMarks);



            }catch (NullPointerException e){

            }catch (IndexOutOfBoundsException ignore){

            }

        }

        @Override
        public int getItemCount() {
            return quizData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_question,tv_correctIncorrect,tv_myAnswer,tv_correctAnswer,tv_marks,question_number;


            public ViewHolder(@NonNull final View itemView) {
                super(itemView);
                tv_question = (TextView) itemView.findViewById(R.id.tv_question);
                tv_correctIncorrect = (TextView) itemView.findViewById(R.id.tv_correctIncorrect);
                tv_myAnswer = (TextView) itemView.findViewById(R.id.tv_myAnswer);
                tv_correctAnswer = (TextView) itemView.findViewById(R.id.tv_correctAnswer);
                tv_marks = (TextView) itemView.findViewById(R.id.tv_marks);
                question_number =(TextView)itemView.findViewById(R.id.question_number);


            }
        }

    }

}
