
package client.legalease.Model.MyDocuments;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("documenttype")
    @Expose
    private String documenttype;
    @SerializedName("doccount")
    @Expose
    private Integer doccount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("alldoc")
    @Expose
    private List<Alldoc> alldoc = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDocumenttype() {
        return documenttype;
    }

    public void setDocumenttype(String documenttype) {
        this.documenttype = documenttype;
    }

    public Integer getDoccount() {
        return doccount;
    }

    public void setDoccount(Integer doccount) {
        this.doccount = doccount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Alldoc> getAlldoc() {
        return alldoc;
    }

    public void setAlldoc(List<Alldoc> alldoc) {
        this.alldoc = alldoc;
    }

}
