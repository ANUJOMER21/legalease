
package client.legalease.Model.QuizModel.QuestionSet;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("quizset")
    @Expose
    private List<Quizset> quizset = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Quizset> getQuizset() {
        return quizset;
    }

    public void setQuizset(List<Quizset> quizset) {
        this.quizset = quizset;
    }
}
