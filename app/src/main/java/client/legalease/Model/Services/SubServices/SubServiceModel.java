
package client.legalease.Model.Services.SubServices;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubServiceModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subservice")
    @Expose
    private List<Subservice> subservice = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Subservice> getSubservice() {
        return subservice;
    }

    public void setSubservice(List<Subservice> subservice) {
        this.subservice = subservice;
    }

}
