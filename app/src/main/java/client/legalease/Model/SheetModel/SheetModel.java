
package client.legalease.Model.SheetModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SheetModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SheetData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SheetData> getData() {
        return data;
    }

    public void setData(List<SheetData> data) {
        this.data = data;
    }

}
