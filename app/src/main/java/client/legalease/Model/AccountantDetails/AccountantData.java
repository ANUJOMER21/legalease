
package client.legalease.Model.AccountantDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountantData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("accountantid")
    @Expose
    private String accountantid;
    @SerializedName("intime")
    @Expose
    private String intime;
    @SerializedName("outtime")
    @Expose
    private String outtime;
    @SerializedName("totalhours")
    @Expose
    private String totalhours;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccountantid() {
        return accountantid;
    }

    public void setAccountantid(String accountantid) {
        this.accountantid = accountantid;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getTotalhours() {
        return totalhours;
    }

    public void setTotalhours(String totalhours) {
        this.totalhours = totalhours;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
