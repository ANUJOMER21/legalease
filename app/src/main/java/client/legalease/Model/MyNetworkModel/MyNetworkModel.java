
package client.legalease.Model.MyNetworkModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyNetworkModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user")
    @Expose
    private List<NetworkUser> user = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NetworkUser> getUser() {
        return user;
    }

    public void setUser(List<NetworkUser> user) {
        this.user = user;
    }

}
