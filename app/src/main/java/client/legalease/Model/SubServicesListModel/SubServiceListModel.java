
package client.legalease.Model.SubServicesListModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubServiceListModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SubservicesListData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubservicesListData> getData() {
        return data;
    }

    public void setData(List<SubservicesListData> data) {
        this.data = data;
    }

}
