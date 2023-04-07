package client.legalease.Model.QualificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QualificationModel {

    @SerializedName("data")
    @Expose
    private List<QualificationData> data = null;

    public List<QualificationData> getQualificationData() {
        return data;
    }

    public void setQualificationData(List<QualificationData> data) {
        this.data = data;
    }

}
