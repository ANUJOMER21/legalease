
package client.legalease.Model.QuizModel.QuestionModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQuestions {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("question")
    @Expose
    private List<Question> question = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }

}
