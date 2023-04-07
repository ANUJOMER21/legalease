
package client.legalease.Model.UploadDocServerModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("require_doc_id")
    @Expose
    private Integer requireDocId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getRequireDocId() {
        return requireDocId;
    }

    public void setRequireDocId(Integer requireDocId) {
        this.requireDocId = requireDocId;
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

}
