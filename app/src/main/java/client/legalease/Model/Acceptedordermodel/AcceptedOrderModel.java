
package client.legalease.Model.Acceptedordermodel;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptedOrderModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("orderslist")
    @Expose
    private Orderslist orderslist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Orderslist getOrderslist() {
        return orderslist;
    }

    public void setOrderslist(Orderslist orderslist) {
        this.orderslist = orderslist;
    }

}
