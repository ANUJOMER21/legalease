package client.legalease.Model.PayTmModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class PayTmData {
    @SerializedName("MID")
    @Expose
    private String mID;
    @SerializedName("ORDER_ID")
    @Expose
    private String oRDERID;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("INDUSTRY_TYPE_ID")
    @Expose
    private String iNDUSTRYTYPEID;
    @SerializedName("CHANNEL_ID")
    @Expose
    private String cHANNELID;
    @SerializedName("TXN_AMOUNT")
    @Expose
    private String tXNAMOUNT;
    @SerializedName("WEBSITE")
    @Expose
    private String wEBSITE;
    @SerializedName("IS_CHECKSUM_VALID")
    @Expose
    private String iSCHECKSUMVALID;

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getORDERID() {
        return oRDERID;
    }

    public void setORDERID(String oRDERID) {
        this.oRDERID = oRDERID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getINDUSTRYTYPEID() {
        return iNDUSTRYTYPEID;
    }

    public void setINDUSTRYTYPEID(String iNDUSTRYTYPEID) {
        this.iNDUSTRYTYPEID = iNDUSTRYTYPEID;
    }

    public String getCHANNELID() {
        return cHANNELID;
    }

    public void setCHANNELID(String cHANNELID) {
        this.cHANNELID = cHANNELID;
    }

    public String getTXNAMOUNT() {
        return tXNAMOUNT;
    }

    public void setTXNAMOUNT(String tXNAMOUNT) {
        this.tXNAMOUNT = tXNAMOUNT;
    }

    public String getWEBSITE() {
        return wEBSITE;
    }

    public void setWEBSITE(String wEBSITE) {
        this.wEBSITE = wEBSITE;
    }

    public String getISCHECKSUMVALID() {
        return iSCHECKSUMVALID;
    }

    public void setISCHECKSUMVALID(String iSCHECKSUMVALID) {
        this.iSCHECKSUMVALID = iSCHECKSUMVALID;
    }
}
