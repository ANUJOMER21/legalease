package client.legalease;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import client.legalease.Model.QuizModel.QuestionModel.GetQuestions;
import client.legalease.Model.QuizModel.QuestionModel.Question;
import client.legalease.Model.QuizModel.QuizAnswerSubmissionModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizTestActivity extends AppCompatActivity {
    String setId = "";
    String setName = "";
    ImageView back;
    TextView quizTitle;
    List<Question> questionList;

    RadioGroup rgroup_option;
    TextView tv_question;
    int optionSize = 0;
    RadioButton rbn;
    Button btn_submitAnswer;
    String answer = "";
    int n = 0;
   String clickedId = "";
    String[] optionName = new String[0];
    HashMap<Integer,String> hashMap = new HashMap<>();
    ProgressDialog pDialog;
    CommonSharedPreference commonSharedPreference;
    String token = "";
    int id = 0;
    String questionId = "";
    CallApi callApi = new CallApi();
    int totalQuestion = 0;
    String resultSuccess = "";
    ProgressBar progressBar;
    TextView current_question;
    TextView tv_displayCurrentQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_test);
        initializeViews();
        questionList = new ArrayList<>();
        commonSharedPreference = new CommonSharedPreference(getApplicationContext());
        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();
            }
        }catch (NullPointerException ignored){

        }
        Log.d("FDds",token);
        Log.d("asd", String.valueOf(id));


        try {
            Intent intent =getIntent();
            setId = intent.getStringExtra("setId");
            setName = intent.getStringExtra("name");

            quizTitle.setText(setName);
        }catch (NullPointerException ignored){

        }

        getMyQuizData(n);
        btn_submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                if (n == totalQuestion-1) {
//                    giveAnswer();
//                    if (resultSuccess.equals("success")) {
//
//                        Intent intent = new Intent(QuizTestActivity.this, FinalQuizResult.class);
//                        intent.putExtra("setId", setId);
//                        startActivity(intent);
//                        finishAffinity();
//                    }else {
//                        Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
//                    }
//                }else {
                    giveAnswer();

                answer.equals("");



            }
        });

    }

    private void giveAnswer() {
        if (answer.equals(null)|| answer.equals("")){
            Toast.makeText(getApplicationContext(),"You haven't selected the answer",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"sucess :- "+ answer,Toast.LENGTH_SHORT).show();

            pDialog = new ProgressDialog(QuizTestActivity.this);
            String pleaseWait = getResources().getString(R.string.please_wait);

            pDialog.setMessage(pleaseWait);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            HashMap<String,String> answerCredential = new HashMap<>();
            answerCredential.put("token",token);
            answerCredential.put("setId",setId);
            answerCredential.put("questionId",questionId);
            answerCredential.put("answer",answer);


            try {
                callApi.requestQuiz(QuizTestActivity.this, answerCredential);
            } catch (Exception e) {
                e.getStackTrace();
            }


        }
    }

    private void getMyQuizData(final int n) {
        progressBar.setVisibility(View.VISIBLE);
            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<GetQuestions> call = api.getQuestion(setId);
            call.enqueue(new Callback<GetQuestions>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<GetQuestions> call, Response<GetQuestions> response) {
                    progressBar.setVisibility(View.GONE);

                    try {
                        questionList =response.body().getQuestion();
                        totalQuestion=questionList.size();
//                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        tv_question.setText(questionList.get(n).getQuestions());
                        questionId = String.valueOf(questionList.get(n).getId());
                        String question_number = String.valueOf(n+1);
                        String tQuestion = String.valueOf(totalQuestion);
                        current_question.setText(question_number+" . ");
                        String hint = question_number +" / "+tQuestion;
                        tv_displayCurrentQuestion.setText(hint);

                        optionName = questionList.get(n).getOptions().split("\\|");
                        optionSize = optionName.length;
                        Log.d("sdfnhbsd", String.valueOf(optionName));
                        Log.d("sdfnhbsd", String.valueOf(optionSize));
//                        Toast.makeText(getApplicationContext(),String.valueOf(optionSize),Toast.LENGTH_SHORT).show();

                        for (int i = 0; i <= optionSize ; i++) {
                            rbn = new RadioButton(getApplicationContext());
                            hashMap.put(i,optionName[i]);

                            final String value= optionName[i];
                            rbn.setId(i);
                            rbn.setBackgroundColor(getResources().getColor(R.color.black_twenty));
                            rbn.setLayoutParams (new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));

                            rbn.setTextColor(Color.BLACK);
                            rbn.setText(value);
                            rbn.setTextSize(17);
                            rbn.setPadding(0, 20, 0, 20);

                            rgroup_option.addView(rbn);
                            rbn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    answer = value;
                                    Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
                                }
                            });








                        }



                    }catch (NullPointerException ignored){

                    }catch (ArrayIndexOutOfBoundsException ignore){

                    }catch (IndexOutOfBoundsException ignor){

                    }




                }

                @Override
                public void onFailure(Call<GetQuestions> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });

        }


        private void initializeViews() {
        quizTitle  =(TextView)findViewById(R.id.quizTitle);
            rgroup_option = (RadioGroup)findViewById(R.id.rgroup_option);
            tv_question = (TextView)findViewById(R.id.tv_question);
            btn_submitAnswer = (Button)findViewById(R.id.btn_submitAnswer);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            current_question =(TextView)findViewById(R.id.current_question);
            tv_displayCurrentQuestion = (TextView)findViewById(R.id.tv_displayCurrentQuestion);
    }

    public void Response_quiz(QuizAnswerSubmissionModel body) {
        pDialog.dismiss();
         resultSuccess = body.getStatus();
            if (body.getStatus().equals(resultSuccess)) {
                tv_question.setText("");
                questionId.equals("");
                answer.equals("");
                rgroup_option.clearCheck();
                rgroup_option.removeAllViews();

                n = n + 1;
                if (n == totalQuestion) {
                    Intent intent = new Intent(QuizTestActivity.this, FinalQuizResult.class);
                    intent.putExtra("setId", setId);
                    startActivity(intent);
                    finishAffinity();
                }else {
                    getMyQuizData(n);

                }

                } else {
            Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void clossDialog() {
        pDialog.dismiss();

        Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
    }
}
