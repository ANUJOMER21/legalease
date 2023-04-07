
package client.legalease.Model.Servicerequestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ServiceRequestModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("orderlist")
    @Expose
    private Orderlist orderlist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Orderlist getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(Orderlist orderlist) {
        this.orderlist = orderlist;
    }

}
