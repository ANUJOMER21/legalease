
package client.legalease.Model.AccountantModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountantModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<AccountantData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AccountantData> getData() {
        return data;
    }

    public void setData(List<AccountantData> data) {
        this.data = data;
    }

}
