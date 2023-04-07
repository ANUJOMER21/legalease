
package client.legalease.Model.UpdateUserProfile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdate {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user")
    @Expose
    private List<User> user = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

}
