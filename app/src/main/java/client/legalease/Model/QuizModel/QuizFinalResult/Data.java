
package client.legalease.Model.QuizModel.QuizFinalResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("qid")
    @Expose
    private Integer qid;
    @SerializedName("setion_id")
    @Expose
    private String setionId;
    @SerializedName("category_id")
    @Expose
    private Object categoryId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("correct_answer")
    @Expose
    private String correctAnswer;
    @SerializedName("givenansweer")
    @Expose
    private String givenansweer;
    @SerializedName("answerstatus")
    @Expose
    private String answerstatus;

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getSetionId() {
        return setionId;
    }

    public void setSetionId(String setionId) {
        this.setionId = setionId;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getGivenansweer() {
        return givenansweer;
    }

    public void setGivenansweer(String givenansweer) {
        this.givenansweer = givenansweer;
    }

    public String getAnswerstatus() {
        return answerstatus;
    }

    public void setAnswerstatus(String answerstatus) {
        this.answerstatus = answerstatus;
    }

}
