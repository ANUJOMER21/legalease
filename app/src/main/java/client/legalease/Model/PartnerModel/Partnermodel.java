
package client.legalease.Model.PartnerModel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Partnermodel {

    @SerializedName("assoicate")
    private Assoicate mAssoicate;
    @SerializedName("status")
    private String mStatus;

    public Assoicate getAssoicate() {
        return mAssoicate;
    }

    public void setAssoicate(Assoicate assoicate) {
        mAssoicate = assoicate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
