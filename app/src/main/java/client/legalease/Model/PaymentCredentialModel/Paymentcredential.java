
package client.legalease.Model.PaymentCredentialModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Paymentcredential {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("merchantId")
    @Expose
    private String merchantId;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("secret_key")
    @Expose
    private String secretKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
