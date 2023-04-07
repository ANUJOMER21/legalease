
package client.legalease.Model.ReachUsModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReachUsModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ReachUsData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReachUsData> getData() {
        return data;
    }

    public void setData(List<ReachUsData> data) {
        this.data = data;
    }

}
