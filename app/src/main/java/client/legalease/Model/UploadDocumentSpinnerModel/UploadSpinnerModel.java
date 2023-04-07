
package client.legalease.Model.UploadDocumentSpinnerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadSpinnerModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("serviceId")
    @Expose
    private String serviceId;
    @SerializedName("require_doc")
    @Expose
    private String requireDoc;
    @SerializedName("nooffile")
    @Expose
    private String nooffile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRequireDoc() {
        return requireDoc;
    }

    public void setRequireDoc(String requireDoc) {
        this.requireDoc = requireDoc;
    }

    public String getNooffile() {
        return nooffile;
    }

    public void setNooffile(String nooffile) {
        this.nooffile = nooffile;
    }

}
