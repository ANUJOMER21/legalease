
package client.legalease.Model.uploadedDocuments;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Uploaddocuments {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum3> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum3> getData() {
        return data;
    }

    public void setData(List<Datum3> data) {
        this.data = data;
    }

}
