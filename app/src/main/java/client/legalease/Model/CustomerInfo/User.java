
package client.legalease.Model.CustomerInfo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id_cms_privileges")
    @Expose
    private Object idCmsPrivileges;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("country")
    @Expose
    private Integer country;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("gst_no")
    @Expose
    private String gstNo;
    @SerializedName("qrcode")
    @Expose
    private Object qrcode;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("referalcode")
    @Expose
    private String referalcode;
    @SerializedName("refferby")
    @Expose
    private String refferby;
    @SerializedName("wallet")
    @Expose
    private Integer wallet;
    @SerializedName("easymoney")
    @Expose
    private Integer easymoney;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("trade")
    @Expose
    private Object trade;
    @SerializedName("industry")
    @Expose
    private Object industry;
    @SerializedName("commission")
    @Expose
    private Integer commission;
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("desination")
    @Expose
    private String desination;
    @SerializedName("short_des")
    @Expose
    private Object shortDes;
    @SerializedName("summary")
    @Expose
    private Object summary;
    @SerializedName("slug")
    @Expose
    private String slug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getIdCmsPrivileges() {
        return idCmsPrivileges;
    }

    public void setIdCmsPrivileges(Object idCmsPrivileges) {
        this.idCmsPrivileges = idCmsPrivileges;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public Object getQrcode() {
        return qrcode;
    }

    public void setQrcode(Object qrcode) {
        this.qrcode = qrcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReferalcode() {
        return referalcode;
    }

    public void setReferalcode(String referalcode) {
        this.referalcode = referalcode;
    }

    public String getRefferby() {
        return refferby;
    }

    public void setRefferby(String refferby) {
        this.refferby = refferby;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public Integer getEasymoney() {
        return easymoney;
    }

    public void setEasymoney(Integer easymoney) {
        this.easymoney = easymoney;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Object getTrade() {
        return trade;
    }

    public void setTrade(Object trade) {
        this.trade = trade;
    }

    public Object getIndustry() {
        return industry;
    }

    public void setIndustry(Object industry) {
        this.industry = industry;
    }

    public Integer getCommission() {
        return commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDesination() {
        return desination;
    }

    public void setDesination(String desination) {
        this.desination = desination;
    }

    public Object getShortDes() {
        return shortDes;
    }

    public void setShortDes(Object shortDes) {
        this.shortDes = shortDes;
    }

    public Object getSummary() {
        return summary;
    }

    public void setSummary(Object summary) {
        this.summary = summary;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

}
