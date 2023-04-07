
package client.legalease.Model.Acceptedordermodel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("merchant_order_id")
    @Expose
    private String merchantOrderId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("assoicate_id")
    @Expose
    private Integer assoicateId;
    @SerializedName("request_id")
    @Expose
    private Integer requestId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("txnid")
    @Expose
    private Object txnid;
    @SerializedName("banktxnid")
    @Expose
    private Object banktxnid;
    @SerializedName("card_holder_name_id")
    @Expose
    private Object cardHolderNameId;
    @SerializedName("currency_code_id")
    @Expose
    private Object currencyCodeId;
    @SerializedName("payment_method")
    @Expose
    private Object paymentMethod;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("serviceId")
    @Expose
    private Object serviceId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_complete_at")
    @Expose
    private String orderCompleteAt;
    @SerializedName("assoicate")
    @Expose
    private Assoicate assoicate;
    @SerializedName("order_service_list")
    @Expose
    private List<OrderService> orderServiceList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAssoicateId() {
        return assoicateId;
    }

    public void setAssoicateId(Integer assoicateId) {
        this.assoicateId = assoicateId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Object getTxnid() {
        return txnid;
    }

    public void setTxnid(Object txnid) {
        this.txnid = txnid;
    }

    public Object getBanktxnid() {
        return banktxnid;
    }

    public void setBanktxnid(Object banktxnid) {
        this.banktxnid = banktxnid;
    }

    public Object getCardHolderNameId() {
        return cardHolderNameId;
    }

    public void setCardHolderNameId(Object cardHolderNameId) {
        this.cardHolderNameId = cardHolderNameId;
    }

    public Object getCurrencyCodeId() {
        return currencyCodeId;
    }

    public void setCurrencyCodeId(Object currencyCodeId) {
        this.currencyCodeId = currencyCodeId;
    }

    public Object getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Object paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getServiceId() {
        return serviceId;
    }

    public void setServiceId(Object serviceId) {
        this.serviceId = serviceId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderCompleteAt() {
        return orderCompleteAt;
    }

    public void setOrderCompleteAt(String orderCompleteAt) {
        this.orderCompleteAt = orderCompleteAt;
    }

    public Assoicate getAssoicate() {
        return assoicate;
    }

    public void setAssoicate(Assoicate assoicate) {
        this.assoicate = assoicate;
    }

    public List<OrderService> getOrderServiceList() {
        return orderServiceList;
    }

    public void setOrderServiceList(List<OrderService> orderServiceList) {
        this.orderServiceList = orderServiceList;
    }

}
