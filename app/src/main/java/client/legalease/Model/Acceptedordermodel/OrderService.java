
package client.legalease.Model.Acceptedordermodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderService {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("assoicate_id")
    @Expose
    private Integer assoicateId;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("sub_title")
    @Expose
    private String subTitle;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("gov_fee")
    @Expose
    private String govFee;
    @SerializedName("professional_fee")
    @Expose
    private String professionalFee;
    @SerializedName("valid")
    @Expose
    private Integer valid;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("meta_title")
    @Expose
    private String metaTitle;
    @SerializedName("meta_keyword")
    @Expose
    private String metaKeyword;
    @SerializedName("meta_description")
    @Expose
    private String metaDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGovFee() {
        return govFee;
    }

    public void setGovFee(String govFee) {
        this.govFee = govFee;
    }

    public String getProfessionalFee() {
        return professionalFee;
    }

    public void setProfessionalFee(String professionalFee) {
        this.professionalFee = professionalFee;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

}
