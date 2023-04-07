
package client.legalease.Model.ServiceOfferModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceOffermodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("offerList")
    @Expose
    private OfferList offerList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OfferList getOfferList() {
        return offerList;
    }

    public void setOfferList(OfferList offerList) {
        this.offerList = offerList;
    }

}
