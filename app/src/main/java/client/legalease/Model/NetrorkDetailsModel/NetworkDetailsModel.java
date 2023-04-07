package client.legalease.Model.NetrorkDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NetworkDetailsModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<NetworkDetailsData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NetworkDetailsData> getData() {
        return data;
    }

    public void setData(List<NetworkDetailsData> data) {
        this.data = data;
    }

}
