
package client.legalease.Model.uploaddocumentlistModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("require_doc")
    @Expose
    private String requireDoc;
    @SerializedName("nooffile")
    @Expose
    private Integer nooffile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequireDoc() {
        return requireDoc;
    }

    public void setRequireDoc(String requireDoc) {
        this.requireDoc = requireDoc;
    }

    public Integer getNooffile() {
        return nooffile;
    }

    public void setNooffile(Integer nooffile) {
        this.nooffile = nooffile;
    }

}
