package client.legalease.Model.QualificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QualificationData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("qualification")
    @Expose
    private String qualification;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
