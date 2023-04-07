
package client.legalease.Model.customerRequestListModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CustomerRequestListmodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("requestlist")
    @Expose
    private Requestlist requestlist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Requestlist getRequestlist() {
        return requestlist;
    }

    public void setRequestlist(Requestlist requestlist) {
        this.requestlist = requestlist;
    }

}
