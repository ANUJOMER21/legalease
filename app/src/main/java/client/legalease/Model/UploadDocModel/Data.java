
package client.legalease.Model.UploadDocModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("serviceId")
    @Expose
    private String serviceId;
    @SerializedName("uploadedType")
    @Expose
    private String uploadedType;
    @SerializedName("uploaded_doc")
    @Expose
    private String uploadedDoc;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUploadedType() {
        return uploadedType;
    }

    public void setUploadedType(String uploadedType) {
        this.uploadedType = uploadedType;
    }

    public String getUploadedDoc() {
        return uploadedDoc;
    }

    public void setUploadedDoc(String uploadedDoc) {
        this.uploadedDoc = uploadedDoc;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
