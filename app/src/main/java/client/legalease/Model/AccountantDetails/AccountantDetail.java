
package client.legalease.Model.AccountantDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountantDetail {

    @SerializedName("data")
    @Expose
    private List<AccountantData> data = null;
    @SerializedName("user")
    @Expose
    private List<Object> user = null;

    public List<AccountantData> getData() {
        return data;
    }

    public void setData(List<AccountantData> data) {
        this.data = data;
    }

    public List<Object> getUser() {
        return user;
    }

    public void setUser(List<Object> user) {
        this.user = user;
    }

}
