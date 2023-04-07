
package client.legalease.Model.SummeryModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SummeryModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SummeryData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SummeryData> getData() {
        return data;
    }

    public void setData(List<SummeryData> data) {
        this.data = data;
    }

}
