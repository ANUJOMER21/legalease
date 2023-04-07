package client.legalease.Model.UserPurchaseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseUserDetails {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("user")
    @Expose
    private List<User> user = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}