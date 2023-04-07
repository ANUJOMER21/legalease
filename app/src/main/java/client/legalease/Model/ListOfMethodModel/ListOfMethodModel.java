
package client.legalease.Model.ListOfMethodModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfMethodModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ListOfMethodData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListOfMethodData> getData() {
        return data;
    }

    public void setData(List<ListOfMethodData> data) {
        this.data = data;
    }

}
