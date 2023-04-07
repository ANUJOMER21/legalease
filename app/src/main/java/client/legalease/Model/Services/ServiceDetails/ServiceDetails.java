
package client.legalease.Model.Services.ServiceDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceDetails {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subservice")
    @Expose
    private List<Subservice> subservice = null;
    @SerializedName("overview")
    @Expose
    private List<Overview> overview = null;
    @SerializedName("benefits")
    @Expose
    private List<Benefit> benefits = null;
    @SerializedName("requisties")
    @Expose
    private List<Requisty> requisties = null;
    @SerializedName("deliverables")
    @Expose
    private List<Deliverable> deliverables = null;
    @SerializedName("faq")
    @Expose
    private List<Faq> faq = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Subservice> getSubservice() {
        return subservice;
    }

    public void setSubservice(List<Subservice> subservice) {
        this.subservice = subservice;
    }

    public List<Overview> getOverview() {
        return overview;
    }

    public void setOverview(List<Overview> overview) {
        this.overview = overview;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public List<Requisty> getRequisties() {
        return requisties;
    }

    public void setRequisties(List<Requisty> requisties) {
        this.requisties = requisties;
    }

    public List<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public List<Faq> getFaq() {
        return faq;
    }

    public void setFaq(List<Faq> faq) {
        this.faq = faq;
    }

}
