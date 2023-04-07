
package client.legalease.Model.MyEasyMoneyModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyEasyMoneyModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<MyEasyMoneyData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyEasyMoneyData> getData() {
        return data;
    }

    public void setData(List<MyEasyMoneyData> data) {
        this.data = data;
    }

}
