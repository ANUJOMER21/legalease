
package client.legalease.Model.AccountModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("data")
    @Expose
    private List<AccountData> data = null;

    public List<AccountData> getAccountData() {
        return data;
    }

    public void setData(List<AccountData> data) {
        this.data = data;
    }

}
