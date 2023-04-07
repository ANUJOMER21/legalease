package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.FinalQuizResult;
import client.legalease.Model.QuizModel.QuestionSet.Quizset;
import client.legalease.QuizTestActivity;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

public class AdapterQuiz  extends RecyclerView.Adapter<AdapterQuiz.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<Quizset> quizsets;

    private Context context;

    public AdapterQuiz(Context context, List<Quizset> quizsets) {
        this.context = context;
        this.quizsets = quizsets;
    }



    @NonNull
    @Override
    public AdapterQuiz.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_questionset, viewGroup, false);


        return new AdapterQuiz.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterQuiz.ViewHolder viewHolder, int i) {

        String title_value="";
        String  content_value="";
        String completed = "";



        try {
            title_value = quizsets.get(i).getName();
            completed = quizsets.get(i).getIscomplete();
            viewHolder.title.setText(title_value);
            if (completed.equals("1")){
                viewHolder.iv_correct.setVisibility(View.VISIBLE);
                viewHolder.btn_result.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_correct.setVisibility(View.GONE);
                viewHolder.btn_result.setVisibility(View.GONE);
            }



        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return quizsets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ProgressBar image_progress;
        ImageView iv_correct;
        Button btn_result;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.content);
            iv_correct = (ImageView)itemView.findViewById(R.id.iv_correct);
            btn_result = (Button) itemView.findViewById(R.id.btn_result);



            btn_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String setId = "";
                    int positon = getAdapterPosition();
                    Quizset mydata = null;
                    try {
                        mydata = quizsets.get(positon);
                        setId = String.valueOf(mydata.getId());

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Intent intent  =new Intent(context, FinalQuizResult.class);
                    intent.putExtra("setId",setId);
                    context.startActivity(intent);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String setId = "";
                    String name = "";
                    String isCompleted = "";
                   int pos = 0;
                    Quizset mydata = null;
                    String m = "";

                    try {
                        pos = getAdapterPosition();
                        mydata = quizsets.get(pos);
                        setId = String.valueOf(mydata.getId());
                        name = mydata.getName();
                        isCompleted  =mydata.getIscomplete();

                       if (isCompleted.equals("1")){
                        Toast.makeText(context,"You have already completed this quiz",Toast.LENGTH_SHORT).show();

                    }else {

                        Intent intent  =new Intent(context, QuizTestActivity.class);
                        intent.putExtra("setId",setId);
                        intent.putExtra("name",name);
                        context.startActivity(intent);
                    }
                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){
                    }
                }
            });


        }
    }

}
