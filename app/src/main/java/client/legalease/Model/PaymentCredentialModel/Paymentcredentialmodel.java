
package client.legalease.Model.PaymentCredentialModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Paymentcredentialmodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("paymentcredentials")
    @Expose
    private List<Paymentcredential> paymentcredentials = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Paymentcredential> getPaymentcredentials() {
        return paymentcredentials;
    }

    public void setPaymentcredentials(List<Paymentcredential> paymentcredentials) {
        this.paymentcredentials = paymentcredentials;
    }

}
