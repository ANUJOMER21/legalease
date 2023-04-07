
package client.legalease.Model.Services.ServicesParentModule;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service")
    @Expose
    private List<Service> service = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

}
