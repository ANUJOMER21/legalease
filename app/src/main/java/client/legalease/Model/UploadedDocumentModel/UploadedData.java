
package client.legalease.Model.UploadedDocumentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadedData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("serviceId")
    @Expose
    private String serviceId;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("uploaded_doc")
    @Expose
    private String uploadedDoc;
    @SerializedName("uploadedType")
    @Expose
    private String uploadedType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("filetypelist")
    @Expose
    private Filetypelist filetypelist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUploadedDoc() {
        return uploadedDoc;
    }

    public void setUploadedDoc(String uploadedDoc) {
        this.uploadedDoc = uploadedDoc;
    }

    public String getUploadedType() {
        return uploadedType;
    }

    public void setUploadedType(String uploadedType) {
        this.uploadedType = uploadedType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Filetypelist getFiletypelist() {
        return filetypelist;
    }

    public void setFiletypelist(Filetypelist filetypelist) {
        this.filetypelist = filetypelist;
    }

}
